package fr.houseofcode.dap.google.web;

import java.io.IOException;
import java.security.GeneralSecurityException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.houseofcode.dap.google.GCalendarService;

/**
 * @author adminHOC
 *
 */
@RestController
@RequestMapping("/events")
public class CalendarController {

    /** Logger.  */
    private static final Logger LOG = LogManager.getLogger();

    /**
     * GCalendarService GCalendarService.
     */
    @Autowired
    private GCalendarService calendarService;

    /**
     * displayNextEvents.
     * @param internalUserid internal user Id
     * @return nextEvent
     * @throws GeneralSecurityException GeneralSecurityException.
     * @throws IOException IOException.
     */
    @RequestMapping("/nextEvent/{userkey}")
    public String getNextEvent(@PathVariable("userkey") final String internalUserid) {

        //GCalendarService srv = GCalendarService.getInstance();

        String event = null;
        try {
            event = calendarService.displayNextEvents(internalUserid, 1);
        } catch (IOException e) {
            LOG.error("Erreur lors de l'appel du next event pour le userId : " + internalUserid, e);
        } catch (GeneralSecurityException e) {
            LOG.error("Erreur lors de l'appel du next event pour le userId : " + internalUserid, e);
        }

        return event;
    }

    /**
     * getXEvents.
     * @param numberOfEventsToDisplay numberOfEventsToDisplay.
     * @param internalUserid internalUserid.
     * @return nextEvent
     */
    @RequestMapping("{numberOfEvent}/{userkey}")
    public String getXEvents(@PathVariable("numberOfEvent") final int numberOfEventsToDisplay,
            @PathVariable("userkey") final String internalUserid) {

        String events = null;

        try {
            events = calendarService.displayNextEvents(internalUserid, numberOfEventsToDisplay);
        } catch (IOException e) {
            LOG.error("Erreur lors de l'appel du next event pour le userId : " + internalUserid, e);
        } catch (GeneralSecurityException e) {
            LOG.error("Erreur lors de l'appel du next event pour le userId : " + internalUserid, e);
        }

        return events;
    }
}
