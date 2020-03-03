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
package org.talend.components.ftp.source;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.talend.components.ftp.service.FTPService;
import org.talend.components.ftp.service.I18nMessage;
import org.talend.sdk.component.api.input.Producer;

import java.io.Serializable;

@Slf4j
@RequiredArgsConstructor
public class FTPInput implements Serializable {

    protected final FTPInputConfiguration configuration;

    protected final FTPService ftpService;

    protected final I18nMessage i18n;

    @Producer
    public Object next() {
        return null;
    }
}
