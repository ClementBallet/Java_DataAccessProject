package fr.houseofcode.dap;

import java.io.IOException;
import java.security.GeneralSecurityException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * App class.
 * @author adminHOC
 *
 */
@SpringBootApplication
public class DapServer {
    //    /** the default internal user. */
    //    private static String defaultUser = "bob";
    //
    //    @Autowired
    //    private GCalendarService calendarService;

    /**
     * Main Method.
     * @param args The args.
     * @throws IOException The IOException.
     * @throws GeneralSecurityException The GeneralSecurityException.
     */
    public static void main(final String... args) throws IOException, GeneralSecurityException {

        SpringApplication.run(DapServer.class, args);

        /*String me = "me";
        Config maConfig = new Config();
        maConfig.setApplicationName("Ma Super Appli");
        Gmailservice gmailService = Gmailservice.getInstance();
        gmailService.setConfiguration(maConfig);
        gmailService.displayLabels(defaultUser, me);
        gmailService.displayNbEmails(defaultUser, me);
        gmailService.displayContentEmails(defaultUser, me);
        calendarService.setConfiguration(maConfig);
        calendarService.displayUpcomingEvents(defaultUser);*/
    }

    /**
     * Load a Web Context config.
     * @return the config
     */
    @Bean
    public Config loadConfig() {
        Config maConfig = new Config();
        maConfig.setApplicationName("Ma Super Appli");
        maConfig.setCredentialsFilePath(System.getProperty("user.home") + System.getProperty("file.separator") + "dap"
                + System.getProperty("file.separator") + "credentials.json");
        maConfig.setTokenDirPath(System.getProperty("user.home") + System.getProperty("file.separator") + "dap");

        return maConfig;
    }
}
