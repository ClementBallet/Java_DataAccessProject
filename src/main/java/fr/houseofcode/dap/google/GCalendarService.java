package fr.houseofcode.dap.google;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Events;

/**
 * @author adminHOC
 *
 */
@Service
public final class GCalendarService extends GoogleService {
    /*** instance. */
    private static volatile GCalendarService instance = null;

    /** Logger.  */
    private static final Logger LOG = LogManager.getLogger();

    /** GMailCalendar. */
    public GCalendarService() {
        super();
    }

    /**
     * getService.
     * @param userId userId.
     * @throws IOException the IOException.
     * @throws GeneralSecurityException The GeneralSecurityException.
     * @return service
     */
    private Calendar getService(final String userId) throws IOException, GeneralSecurityException {
        // Build a new authorized API client service.
        final NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        Credential credentials = getCredentials(userId);

        // Credential getCredentialsGoogleCalendar = getCredentials.getCredentialsGoogleCalendar);
        Calendar service = new Calendar.Builder(httpTransport, getGoogleJsonFactory(), credentials)
                .setApplicationName(getConfiguration().getApplicationName()).build();

        return service;
    }

    /**
     * displayUpcomingEvents.
     * @param internalUserid internal user Id
     * @throws GeneralSecurityException GeneralSecurityException.
     * @throws IOException IOException.
     * @return items Events.
     */
    @RequestMapping("/allEvents/{userkey}")
    public List<Event> displayUpcomingEvents(@PathVariable("userkey") final String internalUserid)
            throws IOException, GeneralSecurityException {
        DateTime now = new DateTime(System.currentTimeMillis());

        Calendar service = getService(internalUserid);

        Events events = service.events().list("primary").setTimeMin(now).setOrderBy("startTime").setSingleEvents(true)
                .execute();

        List<Event> items = events.getItems();
        if (items.isEmpty()) {
            System.out.println("No upcoming events found.");
        } else {
            System.out.printf("Upcoming events :\n");
            for (Event event : items) {
                System.out.printf("%s \n", event.getSummary());
            }
        }
        return items;
    }

    /**
     * displayNextEvents.
     * @param internalUserid internal user Id
     * @param numberOfEventsToDisplay numberOfEventsToDisplay.
     * @return nextEvent
     * @throws GeneralSecurityException GeneralSecurityException.
     * @throws IOException IOException.
     */
    public String displayNextEvents(final String internalUserid, final int numberOfEventsToDisplay)
            throws IOException, GeneralSecurityException {
        // List the next 10 events from the primary calendar.
        DateTime now = new DateTime(System.currentTimeMillis());

        Calendar service = getService(internalUserid);

        Events events = service.events().list("primary").setTimeMin(now).setOrderBy("startTime").setSingleEvents(true)
                .execute();

        List<Event> items = events.getItems();
        String nextEvents = "";

        if (numberOfEventsToDisplay > items.size()) {
            for (int i = 0; i < items.size(); i++) {
                nextEvents += items.get(i).getSummary() + " ";
            }
        } else {
            for (Event item : items) {
                nextEvents += item.getSummary() + " ";
            }
        }

        return nextEvents;
        //return items.get(0).getSummary();

    }
}
