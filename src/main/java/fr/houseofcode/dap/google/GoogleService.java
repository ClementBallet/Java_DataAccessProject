package fr.houseofcode.dap.google;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.file.FileSystemNotFoundException;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.gmail.GmailScopes;

import fr.houseofcode.dap.Config;

/**
 * @author adminHOC
 *
 */
public abstract class GoogleService {

    /** Logger.  */
    private static final Logger LOG = LogManager.getLogger();

    /** JsonFactory to marshal/unMarshall Google messages. */
    private final JsonFactory googleJsonFactory = JacksonFactory.getDefaultInstance();

    /** Application Configuration. */
    @Autowired
    private Config configuration;

    /** Google Authorization Flow. */
    private GoogleAuthorizationCodeFlow flow;

    /** Scopes. */
    private Collection<String> scopes;

    /**
     * Prepare a Google Service.
     * @param config configuration to pickup App specific configuration (credential
     * folder, application name, JSON App credential file, ...)
     */
    public GoogleService(final Config config) {
        this.configuration = config;
    }

    /**
     * Prepare a Google Service.
     */
    public GoogleService() {
        super();
        init();
    }

    /** ini scopes.*/
    private void init() {
        scopes = new ArrayList<String>();
        scopes.add(GmailScopes.GMAIL_LABELS);
        scopes.add(GmailScopes.GMAIL_READONLY);
        scopes.add(CalendarScopes.CALENDAR_READONLY);
    }

    /**
     * Creates an authorized Credential object.
     * @param userId the App User ID
     * @return An authorized Credential object.
     * @throws IOException If there is no client_secret.
     */
    public Credential getCredentials(final String userId) throws IOException {
        return getFlow().loadCredential(userId);
        // installed App code
        // return new AuthorizationCodeInstalledApp(flow, new
        // LocalServerReceiver()).authorize("user");
    }

    /**
     * Initialize Google flow (if not already initialized) and return it.
     * @return a Google Authorization Flow
     * @throws IOException if Google Error occurs
     */
    public GoogleAuthorizationCodeFlow getFlow() throws IOException {
        if (null == flow) {
            flow = initializeFlow();
        }
        return flow;
    }

    /**
     * Load "credentail.json" file {@link Config.getCredentialsFilePath}.
     * @return the GoogleClientSecrets
     * @throws IOException if file not exists
     */
    private GoogleClientSecrets loadCredentialFile() throws IOException {
        // Load client secrets.
        Reader appClientSecret = null;
        final File appClientSecretFile = new File(configuration.getCredentialsFilePath());
        if (appClientSecretFile.exists()) {
            appClientSecret = new InputStreamReader(new FileInputStream(appClientSecretFile), Charset.forName("UTF-8"));
        } else {
            // try with app local data (not recommended to store this file in public
            // repository)
            final InputStream appClientSecretStream = GoogleService.class
                    .getResourceAsStream(configuration.getCredentialsFilePath());
            if (null != appClientSecretStream) {
                appClientSecret = new InputStreamReader(appClientSecretStream, Charset.forName("UTF-8"));
            }
        }
        if (null == appClientSecret) {
            final String message = "No AppCredentialFile to connect to Google App. This file should be in : "
                    + configuration.getCredentialsFilePath();
            LOG.error(message);
            throw new FileSystemNotFoundException(message);
        }
        final GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(getGoogleJsonFactory(), appClientSecret);

        return clientSecrets;
    }

    /**
     * Initialize a Google Flow.
     * @return the Google Flow
     * @throws IOException if Google Error occurs
     */
    public GoogleAuthorizationCodeFlow initializeFlow() throws IOException {
        GoogleClientSecrets clientSecrets = loadCredentialFile();
        // Build flow and trigger user authorization request.
        final GoogleAuthorizationCodeFlow newFlow = new GoogleAuthorizationCodeFlow.Builder(new NetHttpTransport(),
                getGoogleJsonFactory(), clientSecrets, scopes)
                        .setDataStoreFactory(new FileDataStoreFactory(new File(configuration.getTokensDirectoryPath())))
                        .setAccessType("offline").build();
        return newFlow;
    }

    /**
     * @return the googleJsonFactory
     */
    protected JsonFactory getGoogleJsonFactory() {
        return googleJsonFactory;
    }

    /**
     * @return the configuration
     */
    public final Config getConfiguration() {
        return configuration;
    }

    /**
     * @param config the configuration to set
     */
    public void setConfiguration(final Config config) {
        this.configuration = config;
    }
}
