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
 *
 */

package org.talend.components.azure.eventhubs.source.batch;

import static java.util.Collections.singletonList;

import java.io.Serializable;
import java.util.List;

import org.talend.components.azure.eventhubs.service.Messages;
import org.talend.components.azure.eventhubs.service.UiActionService;
import org.talend.sdk.component.api.component.Icon;
import org.talend.sdk.component.api.component.Version;
import org.talend.sdk.component.api.configuration.Option;
import org.talend.sdk.component.api.input.Assessor;
import org.talend.sdk.component.api.input.Emitter;
import org.talend.sdk.component.api.input.PartitionMapper;
import org.talend.sdk.component.api.input.PartitionSize;
import org.talend.sdk.component.api.input.Split;
import org.talend.sdk.component.api.meta.Documentation;
import org.talend.sdk.component.api.service.record.RecordBuilderFactory;

@Version(1)
@Icon(value = Icon.IconType.AZURE_EVENT_HUBS)
@PartitionMapper(name = "AzureEventHubsInputMapper")
@Documentation("Mapper to consume mesage from eventhubs")
public class AzureEventHubsInputMapper implements Serializable {

    private final AzureEventHubsInputConfiguration configuration;

    private final UiActionService service;

    private final RecordBuilderFactory recordBuilderFactory;

    private Messages messages;

    public AzureEventHubsInputMapper(@Option("configuration") final AzureEventHubsInputConfiguration configuration,
            final UiActionService service, final RecordBuilderFactory recordBuilderFactory, final Messages messages) {
        this.configuration = configuration;
        this.service = service;
        this.recordBuilderFactory = recordBuilderFactory;
        this.messages = messages;
    }

    @Assessor
    public long estimateSize() {
        return 1L;
    }

    @Split
    public List<AzureEventHubsInputMapper> split(@PartitionSize final long bundles) {
        return singletonList(this);
    }

    @Emitter
    public AzureEventHubsSource createWorker() {
        return new AzureEventHubsSource(configuration, service, recordBuilderFactory, messages);
    }
}