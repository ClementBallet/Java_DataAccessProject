package fr.houseofcode.dap.google.web;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.api.services.gmail.model.Label;

import fr.houseofcode.dap.google.Gmailservice;

/**
 * GmailController GmailController.
 * @author adminHOC
 *
 */
@RestController
@RequestMapping("/emails")
public class GmailController {

    /** Logger.  */
    private static final Logger LOG = LogManager.getLogger();

    /**
     * gmailService gmailService.
     */
    @Autowired
    private Gmailservice gmailService;

    /**
     * displayNbEmails.
     * @param internalUserId Internal user Id
     * @param user user.
     * @throws IOException IOE.
     * @throws GeneralSecurityException if Google Error.
     * @return getEmailList service.
     */
    @RequestMapping("/unread/{guserid}")
    public String getUnreadNbEmails(@RequestParam("userkey") final String internalUserId,
            @PathVariable("guserid") final String user) throws IOException, GeneralSecurityException {

        String unreadEmails = null;
        try {
            unreadEmails = gmailService.displayNbEmails(internalUserId, user);
        } catch (IOException e) {
            LOG.error("Erreur lors de l'appel des emails non lus pour le userId : " + internalUserId, e);
        } catch (GeneralSecurityException e) {
            LOG.error("Erreur lors de l'appel des emails non lus pour le userId : " + internalUserId, e);
        }

        return unreadEmails;
    }

    /**
     * displayLabels.
     * @param internalUserId internal user Id
     * @param user The user.
     * @throws GeneralSecurityException The GeneralSecurityException.
     * @throws IOException The IOExcpetion.
     * @return labels listResponse.
     */
    @RequestMapping(path = "/labels/{guserid}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<Label> getLabelsJson(@RequestParam("userkey") final String internalUserId,
            @PathVariable("guserid") final String user) throws GeneralSecurityException, IOException {

        List<Label> labelsJson = null;

        try {
            labelsJson = gmailService.displayLabels(internalUserId, user);
        } catch (IOException e) {
            LOG.error("Erreur lors de l'appel du label au format JSON pour le userId : " + internalUserId, e);
        } catch (GeneralSecurityException e) {
            LOG.error("Erreur lors de l'appel du label au format JSON pour le userId : " + internalUserId, e);
        }

        return labelsJson;
    }

    /**
     * getLabelsName.
     * @param internalUserId internalUserId.
     * @param user user.
     * @return String.
     */
    @RequestMapping(path = "/labels/{guserid}", produces = MediaType.TEXT_PLAIN_VALUE)
    public String getLabelsName(@RequestParam("userkey") final String internalUserId,
            @PathVariable("guserid") final String user) {

        String labelsList = null;

        try {
            labelsList = gmailService.displayLabelsName(internalUserId, user);
        } catch (IOException e) {
            LOG.error("Erreur lors de l'appel du label pour le userId : " + internalUserId, e);
        } catch (GeneralSecurityException e) {
            LOG.error("Erreur lors de l'appel du label pour le userId : " + internalUserId, e);
        }

        return labelsList;
    }
}
