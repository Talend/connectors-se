/*
 * Copyright (C) 2006-2019 Talend Inc. - www.talend.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */

package org.talend.components.azure.runtime.input;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.LinkedList;

import org.apache.avro.generic.GenericRecord;
import org.apache.commons.compress.utils.IOUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.parquet.avro.AvroParquetReader;
import org.apache.parquet.hadoop.ParquetReader;
import org.apache.parquet.hadoop.util.HadoopInputFile;
import org.apache.parquet.io.InputFile;
import org.talend.components.azure.dataset.AzureBlobDataset;
import org.talend.components.azure.runtime.converters.ParquetConverter;
import org.talend.components.azure.service.AzureBlobComponentServices;
import org.talend.sdk.component.api.record.Record;
import org.talend.sdk.component.api.service.record.RecordBuilderFactory;

import com.microsoft.azure.storage.StorageException;
import com.microsoft.azure.storage.blob.ListBlobItem;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ParquetBlobFileReader extends BlobFileReader {

    public ParquetBlobFileReader(AzureBlobDataset config, RecordBuilderFactory recordBuilderFactory,
            AzureBlobComponentServices connectionServices) throws URISyntaxException, StorageException {
        super(config, recordBuilderFactory, connectionServices);
    }

    @Override
    protected ItemRecordIterator initItemRecordIterator(Iterable<ListBlobItem> blobItems) {
        return new ParquetRecordIterator(blobItems);
    }

    private class ParquetRecordIterator extends ItemRecordIterator<GenericRecord> {

        private LinkedList<GenericRecord> recordList;

        private ParquetConverter converter;

        private ParquetRecordIterator(Iterable<ListBlobItem> blobItemsList) {
            super(blobItemsList);
            this.recordList = new LinkedList<>();
            takeFirstItem();
        }

        @Override
        protected Record convertToRecord(GenericRecord next) {
            if (converter == null) {
                converter = ParquetConverter.of(getRecordBuilderFactory());
            }

            return converter.toRecord(next);
        }

        @Override
        protected void readItem() {
            try (InputStream input = getCurrentItem().openInputStream()) {
                Path tmp = Files.createTempFile("tempFile", ".parquet");

                Files.copy(input, tmp, StandardCopyOption.REPLACE_EXISTING);
                IOUtils.closeQuietly(input);
                InputFile file = HadoopInputFile.fromPath(new org.apache.hadoop.fs.Path(tmp.toFile().getPath()),
                        new Configuration());
                ParquetReader<GenericRecord> reader = AvroParquetReader.<GenericRecord> builder(file).build();

                GenericRecord record;
                while ((record = reader.read()) != null) {
                    recordList.add(record);
                }
            } catch (IOException | StorageException e) {
                log.error(e.getMessage(), e);
            }
        }

        @Override
        protected boolean hasNextRecordTaken() {
            return !recordList.isEmpty();
        }

        @Override
        protected GenericRecord takeNextRecord() {
            return recordList.poll();
        }

        @Override
        protected void complete() {
            // NOOP
        }
    }

}
