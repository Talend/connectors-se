/*
 * Copyright (C) 2006-2018 Talend Inc. - www.talend.com
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

package org.talend.components.azure.common.runtime.input.excel;

import java.net.URISyntaxException;

import org.apache.poi.ss.usermodel.Row;
import org.talend.components.azure.common.runtime.input.BlobFileReader;
import org.talend.components.azure.dataset.AzureBlobDataset;
import org.talend.components.azure.service.AzureBlobConnectionServices;
import org.talend.sdk.component.api.record.Record;
import org.talend.sdk.component.api.service.record.RecordBuilderFactory;

import com.microsoft.azure.storage.StorageException;
import com.microsoft.azure.storage.blob.ListBlobItem;

public class ExcelHTMLBlobFileReader extends ExcelBlobFileReader {

    public ExcelHTMLBlobFileReader(AzureBlobDataset config, RecordBuilderFactory recordBuilderFactory,
            AzureBlobConnectionServices connectionServices) throws URISyntaxException, StorageException {
        super(config, recordBuilderFactory, connectionServices);
    }


    private class ExcelRecordIterator extends BlobFileReader.ItemRecordIterator<Row> {

        public ExcelRecordIterator(Iterable<ListBlobItem> blobItemsList) {
            super(blobItemsList);
        }

        @Override
        protected void initRecordContainer() {

        }

        @Override
        protected Row takeNextRecord() {
            return null;
        }

        @Override
        protected boolean hasNextRecordTaken() {
            return false;
        }

        @Override
        protected Record convertToRecord(Row next) {
            return null;
        }

        @Override
        protected void readItem() {

        }
    }
}
