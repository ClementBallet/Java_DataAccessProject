package fr.houseofcode.dap.google;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.Label;
import com.google.api.services.gmail.model.ListLabelsResponse;
import com.google.api.services.gmail.model.ListMessagesResponse;
import com.google.api.services.gmail.model.Message;
import com.google.api.services.gmail.model.MessagePartHeader;

/**
 * @author adminHOC
 *
 */
@Service
public class Gmailservice extends GoogleService {

    /** Logger. */
    private static final Logger LOG = LogManager.getLogger();

    /** prepare for Gmail Action. */
    public Gmailservice() {
        super();
    }

    /**
     * Build a New Mail service.
     * @param userId The internal APP userId.
     * @return a configured Mail Service
     * @throws IOException if Google crash
     * @throws GeneralSecurityException if Google crash
     */
    private Gmail getService(final String userId) throws GeneralSecurityException, IOException {
        // Build a new authorized API client service.
        final NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();

        Credential credentials = getCredentials(userId);

        Gmail service = new Gmail.Builder(httpTransport, getGoogleJsonFactory(), credentials)
                .setApplicationName(getConfiguration().getApplicationName()).build();

        return service;
    }

    /**
     * getMessage Method.
     * @param service The service.
     * @param userId The UserId.
     * @param messageId The MessageId.
     * @return String
     * @throws IOException The IOException.
     */
    private Message getMessage(final Gmail service, final String userId, final String messageId) throws IOException {
        Message message = service.users().messages().get(userId, messageId).execute();

        List<MessagePartHeader> headers = message.getPayload().getHeaders();
        String subject = "sans Sujet";

        for (MessagePartHeader messagePart : headers) {
            if (messagePart.getName().equals("Subject")) {
                subject = messagePart.getValue();
                break;
            }
        }

        System.out.println("Message snippet: " + subject + " : " + message.getSnippet());

        return message;
    }

    /**
     * displayLabels.
     * @param internalUserId internal user Id
     * @param user The user.
     * @throws GeneralSecurityException The GeneralSecurityException.
     * @throws IOException The IOExcpetion.
     * @return labels listResponse.
     */
    public List<Label> displayLabels(final String internalUserId, @PathVariable("guserid") final String user)
            throws GeneralSecurityException, IOException {
        // Print the labels in the user's account.
        Gmail service = getService(internalUserId);
        ListLabelsResponse listResponse = service.users().labels().list(user).execute();

        List<Label> labels = listResponse.getLabels();

        return labels;
    }

    /**
     * displayLabels.
     * @param internalUserId internal user Id
     * @param user The user.
     * @throws GeneralSecurityException The GeneralSecurityException.
     * @throws IOException The IOExcpetion.
     * @return labels listResponse.
     */
    public String displayLabelsName(final String internalUserId, final String user)
            throws GeneralSecurityException, IOException {
        String labelString = "";
        List<Label> labels = displayLabels(internalUserId, user);
        if (labels.isEmpty()) {
            labelString = "No labels found.";
        } else {
            for (Label label : labels) {
                labelString += label.getName() + ", ";
            }
        }
        return labelString;
    }

    /**
     * getEmailsList.
     * @param service service.
     * @param user user.
     * @return messages messages.
     * @throws IOException IOE.
     */
    private List<Message> getEmailsList(final Gmail service, final String user) throws IOException {
        List<Message> messages = new ArrayList<Message>();
        String query = "in:inbox is:unread";
        ListMessagesResponse response = service.users().messages().list(user).setQ(query).execute();
        while (response.getMessages() != null) {
            messages.addAll(response.getMessages());
            if (response.getNextPageToken() != null) {
                final Long maxl = 10L;
                String pageToken = response.getNextPageToken();
                response = service.users().messages().list(user).setQ(query).setPageToken(pageToken).setMaxResults(maxl)
                        .execute();
                messages.addAll(response.getMessages());

            } else {
                break;
            }
        }
        return messages;
    }

    /**
     * displayNbEmails.
     * @param internalUserId Internal user Id
     * @param user user.
     * @throws IOException IOE.
     * @throws GeneralSecurityException if Google Error.
     * @return getEmailList service.
     */
    public String displayNbEmails(final String internalUserId, final String user)
            throws IOException, GeneralSecurityException {
        Gmail service = getService(internalUserId);

        System.out.println("Nombre de message : " + getEmailsList(service, user).size());

        LOG.info("NB unread emails :" + getEmailsList(service, user).size());

        return "" + getEmailsList(service, user).size();
    }

    /**
     * The DisplayContentEmail.
     * @param user The user.
     * @param internalUserId internal User Id
     * @throws IOException The IOException.
     * @throws GeneralSecurityException if google error
     */
    public void displayContentEmails(final String internalUserId, final String user)
            throws IOException, GeneralSecurityException {
        Gmail service = getService(internalUserId);

        List<Message> messages = getEmailsList(service, user);

        for (Message message : messages) {
            System.out.println(message.toPrettyString());
            getMessage(service, user, message.getId());
        }
    }

}
