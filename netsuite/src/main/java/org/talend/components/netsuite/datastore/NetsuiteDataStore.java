package org.talend.components.netsuite.datastore;

import org.talend.components.netsuite.service.UIActionService;
import org.talend.sdk.component.api.configuration.Option;
import org.talend.sdk.component.api.configuration.action.Checkable;
import org.talend.sdk.component.api.configuration.condition.ActiveIf;
import org.talend.sdk.component.api.configuration.type.DataStore;
import org.talend.sdk.component.api.configuration.ui.layout.GridLayout;
import org.talend.sdk.component.api.configuration.ui.layout.GridLayouts;
import org.talend.sdk.component.api.configuration.ui.widget.Credential;
import org.talend.sdk.component.api.meta.Documentation;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@DataStore("NetsuiteConnection")
@Checkable(UIActionService.HEALTH_CHECK)
@GridLayouts({
        @GridLayout({ @GridLayout.Row({ "endpoint" }), @GridLayout.Row({ "apiVersion" }), @GridLayout.Row({ "loginType" }),
                @GridLayout.Row({ "email" }), @GridLayout.Row({ "password" }), @GridLayout.Row({ "role" }),
                @GridLayout.Row({ "account" }), @GridLayout.Row({ "applicationId" }),
                @GridLayout.Row({ "consumerKey", "consumerSecret" }), @GridLayout.Row({ "tokenId", "tokenSecret" }) }),
        @GridLayout(names = { GridLayout.FormType.ADVANCED }, value = { @GridLayout.Row({ "enableCustomization" }) }) })
@Documentation("Provides all needed properties for establishing connection")
public class NetsuiteDataStore {

    @Option
    @Documentation("NetSuite endpoint to connect")
    private String endpoint = "https://webservices.na2.netsuite.com/services/NetSuitePort_2018_2";

    @Option
    @Documentation("NetSuite API version")
    private ApiVersion apiVersion = ApiVersion.V2018_2;

    @Option
    @Documentation("Login Type. By default - BASIC, connects using email and password; TBA - token-based authentication, connects using tokens")
    private LoginType loginType = LoginType.BASIC;

    @Option
    @ActiveIf(target = "loginType", value = "BASIC")
    @Documentation("User email address")
    private String email;

    @Option
    @ActiveIf(target = "loginType", value = "BASIC")
    @Credential
    @Documentation("User password")
    private String password;

    @Option
    @ActiveIf(target = "loginType", value = "BASIC")
    @Documentation("Role assigned")
    private int role;

    @Option
    @Documentation("NetSuite company account")
    private String account;

    @Option
    @ActiveIf(target = "loginType", value = "BASIC")
    @Documentation("Application ID specified for WebService login")
    private String applicationId;

    @Option
    @ActiveIf(target = "loginType", value = "TBA")
    @Documentation("Consumer Key that is used for Token-Based authentication")
    private String consumerKey;

    @Option
    @ActiveIf(target = "loginType", value = "TBA")
    @Credential
    @Documentation("Consumer Secret that is used for Token-Based authentication")
    private String consumerSecret;

    @Option
    @ActiveIf(target = "loginType", value = "TBA")
    @Documentation("Token Id that is used for Token-Based authentication")
    private String tokenId;

    @Option
    @ActiveIf(target = "loginType", value = "TBA")
    @Credential
    @Documentation("Token Secret that is used for Token-Based authentication")
    private String tokenSecret;

    @Option
    @Documentation("Enables or disables operations with custom records, fields, entities, forms.")
    private boolean enableCustomization = true;

    /**
     * Supported NetSuite API versions.
     *
     */
    @AllArgsConstructor
    public enum ApiVersion {
        V2018_2("2018.2");

        private String version;

        public String getVersion() {
            return this.version;
        }
    }

    /**
     * Supported Login Types.
     *
     */
    public enum LoginType {
        BASIC,
        TBA;

    }
}
