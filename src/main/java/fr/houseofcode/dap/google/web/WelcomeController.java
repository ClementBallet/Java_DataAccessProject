package fr.houseofcode.dap.google.web;

import java.io.IOException;
import java.security.GeneralSecurityException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import fr.houseofcode.dap.google.Gmailservice;

/**
 * @author adminHOC
 *
 */
@Controller
public class WelcomeController {

    /**
     * Gmailservice Gmailservice.
     */
    @Autowired
    private Gmailservice gmailService;

    /**
     * Request view welcome.html.
     * @param model model.
     * @return Welcome
     */
    @RequestMapping("/")
    public String welcome(final Model model) {
        String nbEmail = null;
        try {
            nbEmail = gmailService.displayNbEmails("bob", "me");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (GeneralSecurityException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        model.addAttribute("username", nbEmail);
        return "Welcome";
    }
}
