/*
 * Copyright (C) 2006-2020 Talend Inc. - www.talend.com
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
package org.talend.components.cosmosDB.input;

import org.talend.components.cosmosDB.service.CosmosDBService;
import org.talend.components.cosmosDB.service.I18nMessage;
import org.talend.sdk.component.api.component.Icon;
import org.talend.sdk.component.api.component.Version;
import org.talend.sdk.component.api.configuration.Option;
import org.talend.sdk.component.api.input.Emitter;
import org.talend.sdk.component.api.meta.Documentation;
import org.talend.sdk.component.api.service.record.RecordBuilderFactory;

import lombok.extern.slf4j.Slf4j;

@Version(1)
@Slf4j
@Documentation("This component reads data from cosmosDB.")
@Emitter(name = "CollectionID")
@Icon(value = Icon.IconType.CUSTOM, custom = "CosmosDBInput")
public class CosmosDBCollectionInput extends AbstractInput {

    public CosmosDBCollectionInput(@Option("configuration") final CosmosDBCollectionInputConfiguration configuration,
            final CosmosDBService service, final RecordBuilderFactory builderFactory, final I18nMessage i18n) {
        super(configuration, service, builderFactory, i18n);

    }
}