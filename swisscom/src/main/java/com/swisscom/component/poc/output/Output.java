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
package com.swisscom.component.poc.output;

import com.swisscom.component.poc.config.Config;
import com.swisscom.component.poc.service.SwisscomService;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.talend.sdk.component.api.component.Icon;
import org.talend.sdk.component.api.component.Version;
import org.talend.sdk.component.api.configuration.Option;
import org.talend.sdk.component.api.meta.Documentation;
import org.talend.sdk.component.api.processor.ElementListener;
import org.talend.sdk.component.api.processor.Processor;
import org.talend.sdk.component.api.record.Record;

import javax.annotation.PostConstruct;
import java.io.Serializable;

@Slf4j
@Getter
@Version
@Processor(name = "Output")
@Icon(value = Icon.IconType.CUSTOM, custom = "test")
@Documentation("")
public class Output implements Serializable {

    private Config config;

    public Output(@Option("configuration") final Config config, final SwisscomService service) {
        this.config = config;
    }

    @PostConstruct
    public void init() {
        // no-op
    }

    @ElementListener
    public void process(final Record input) {
        log.info("Swisscom" + input);
    }

}
