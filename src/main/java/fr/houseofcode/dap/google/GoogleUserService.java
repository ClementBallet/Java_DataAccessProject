package fr.houseofcode.dap.google;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.google.api.client.auth.oauth2.StoredCredential;
import com.google.api.client.util.store.DataStore;
import com.google.api.client.util.store.MemoryDataStoreFactory;

/**
 * GoogleUserService GoogleUserService.
 * @author adminHOC
 *
 */
@Service
public class GoogleUserService extends GoogleService {

    /** Logger. */
    private static final Logger LOG = LogManager.getLogger();

    /**
     * getUserToken getUserToken.
     * @return String.
     */
    public DataStore<StoredCredential> getUserToken() {
        DataStore<StoredCredential> response = null;

        try {
            response = getFlow().getCredentialDataStore();
        } catch (IOException ioe) {
            LOG.error("Error while trying to load Google flow", ioe);
            try {
                response = MemoryDataStoreFactory.getDefaultInstance().getDataStore("fake");
            } catch (IOException ioe2) {
                LOG.error("Cannot create a fake memoryDataStore", ioe2);
            }
        }

        return response;
    }
}
