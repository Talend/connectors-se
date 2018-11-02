package org.talend.components.azure.common;

import lombok.Data;

import org.talend.components.azure.service.AzureComponentServices;
import org.talend.sdk.component.api.configuration.Option;
import org.talend.sdk.component.api.configuration.action.Suggestable;
import org.talend.sdk.component.api.configuration.type.DataSet;
import org.talend.sdk.component.api.meta.Documentation;

@Data
@DataSet("AzureDataSet")
public class AzureTableConnection {

    @Option
    @Documentation("Azure Connection")
    private AzureConnection connection;

    @Option
    @Documentation("The name of the table to access")
    @Suggestable(value = AzureComponentServices.GET_TABLE_NAMES, parameters = "connection")
    private String tableName;
}
