package fr.houseofcode.dap.google.web;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.api.client.auth.oauth2.StoredCredential;
import com.google.api.client.util.store.DataStore;

import fr.houseofcode.dap.google.GoogleUserService;

/**
 * @author adminHOC
 *
 */
@Controller
public class AdminController {

    /**
     * googleUserService googleUserService.
     */
    @Autowired
    private GoogleUserService googleUserService;

    /**
     * Request view credentials.html.
     * @param model model.
     * @throws IOException IOException.
     * @throws GeneralSecurityException GeneralSecurityException.
     */
    @RequestMapping("/admin")
    public void credentials(final Model model) throws IOException, GeneralSecurityException {

        DataStore<StoredCredential> credentials = null;

        credentials = googleUserService.getUserToken();

        Iterable<String> keys = credentials.keySet();

        Map<String, StoredCredential> mapCredentials = new HashMap<>();

        for (String key : keys) {
            mapCredentials.put(key, credentials.get(key));
            model.addAttribute("credentials", mapCredentials);
        }

    }
}
