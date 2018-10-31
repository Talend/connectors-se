package org.talend.components.onedrive.service.http;

import lombok.extern.slf4j.Slf4j;
import org.talend.components.onedrive.helpers.StringHelper;
import org.talend.sdk.component.api.service.Service;
import org.talend.sdk.component.api.service.http.Response;

import javax.annotation.PostConstruct;
import javax.json.JsonObject;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Service
@Slf4j
public class OneDriveAuthHttpClientService {

    private final static String AUTH_SERVER = "https://login.microsoftonline.com/";

    private final static String AUTH_PATH = "/oauth2/token";

    private final static String RESOURCE = "https://graph.microsoft.com/";

    private final static String GRANT_TYPE = "password";

    private final static String ACCESS_TOKEN_FIELD = "access_token";

    @Service
    private OneDriveAuthHttpClient oneDriveAuthHttpClient = null;

    @PostConstruct
    public void init() {
        setBase();
    }

    private void setBase() {
        oneDriveAuthHttpClient.base(AUTH_SERVER);
    }

    public String getToken(String tenantId, String client_id, String login, String password)
            throws UnsupportedEncodingException, BadCredentialsException {
        String requestPath = tenantId + AUTH_PATH;
        String body = "client_id=" + client_id + "&resource=" + URLEncoder.encode(RESOURCE, StringHelper.STRING_CHARSET)
                + "&grant_type=" + GRANT_TYPE + "&username=" + URLEncoder.encode(login, StringHelper.STRING_CHARSET)
                + "&password=" + URLEncoder.encode(password, "UTF-8");

        Response<JsonObject> response = oneDriveAuthHttpClient.getToken(requestPath, body);
        String accessToken = null;
        if (response.status() == 200) {
            JsonObject responseBody = response.body();
            // convert json-string to string
            accessToken = responseBody.getString(ACCESS_TOKEN_FIELD);
        }

        if (accessToken == null || accessToken.isEmpty()) {
            throw new BadCredentialsException("Get user's token exception (token is not set)");
        }

        return accessToken;
    }
}