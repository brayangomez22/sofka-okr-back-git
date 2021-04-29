package co.com.sofka.okrs.util.calendar;


import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.calendar.CalendarScopes;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.List;

public class CalendarUtil {

    public static final String APPLICATION_NAME = "Google Calendar API";
    public static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final String TOKENS_DIRECTORY_PATH = "tokens";

    private static final List<String> SCOPES = Collections.singletonList(CalendarScopes.CALENDAR);
    private static final String CREDENTIALS_FILE_PATH = "/credentials.json";


    public static Credential getCredentials(final NetHttpTransport HTTP_TRANSPO) throws IOException {
        var clientSecrets = loadClientSecrets();
        return triggerUserAuthorization(HTTP_TRANSPO, clientSecrets);
    }

    private static GoogleClientSecrets loadClientSecrets() throws IOException {
        var in = CalendarUtil.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
        if(in == null){
            throw new FileNotFoundException("Resource not found: $CREDENTIALS_FILE_PATH");
        }
        return GoogleClientSecrets.load(JSON_FACTORY,new InputStreamReader(in));
    }

    private static LocalServerReceiver receiver(){
        return new LocalServerReceiver.Builder().setPort(8880).build();
    }

    private static Credential triggerUserAuthorization(final NetHttpTransport HTTP_TRANSPORT, GoogleClientSecrets clientSecrets) throws IOException {
        var flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
                .setAccessType("offline")
                .build();
        return new AuthorizationCodeInstalledApp(flow, receiver()).authorize("user");
    }






}
