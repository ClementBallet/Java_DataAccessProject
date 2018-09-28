package fr.houseofcode.dap;

/**
 * Config.
 * @author adminHOC
 *
 */
public final class Config {

    /** CREDENTIALS_FOLDER. */
    /** APPLICATION_NAME. */
    private static final String APPLICATION_NAME = "HoC DaP";
    /** TOKENS_DIRECTORY_PATH. */
    private static final String TOKENS_DIRECTORY_PATH = "tokens";
    /** CREDENTIALS_FILE_PATH. */
    private static final String CREDENTIALS_FILE_PATH = "/credentials.json";
    /**
     * O_AUTH_2_CALL_BACK_URL.
     */
    private static final String O_AUTH_2_CALL_BACK = "/oAuth2Callback";

    /** applicationName. */
    private String applicationName;
    /** clientSecretFile. */
    private String tokenDirPath;
    /** clientSecretFile. */
    private String credentialsFilePath;
    /**
     * oAuth2CallbackUrl.
     */
    private String oAuth2CallbackUrl;

    /**
     * Config constructor.
     */
    public Config() {
        super();
        applicationName = APPLICATION_NAME;
        tokenDirPath = TOKENS_DIRECTORY_PATH;
        credentialsFilePath = CREDENTIALS_FILE_PATH;
        oAuth2CallbackUrl = O_AUTH_2_CALL_BACK;
    }

    /**
     * @return the applicationName
     */
    public String getApplicationName() {
        return applicationName;
    }

    /**
     * @param a the applicationName to set
     */
    public void setApplicationName(final String a) {
        this.applicationName = a;
    }

    /**
     * @return the tokensDirectoryPath
     */
    public String getTokensDirectoryPath() {
        return tokenDirPath;
    }

    /**
     * @param tokenDir the tokenDirPath to set
     */
    public void setTokenDirPath(final String tokenDir) {
        this.tokenDirPath = tokenDir;
    }

    /**
     * @return the credentialsFilePath
     */
    public String getCredentialsFilePath() {
        return credentialsFilePath;
    }

    /**
     * @param credentialsFile the credentialsFilePath to set
     */
    public void setCredentialsFilePath(final String credentialsFile) {
        this.credentialsFilePath = credentialsFile;
    }

    /**
     * @return oAuth2CallbackUrl.
     */
    public String getoAuth2CallbackUrl() {
        return oAuth2CallbackUrl;
    }

    /**
     * @param oAuth2Callback the oAuth2CallbackUrl to set
     */
    public void setoAuth2CallbackUrl(final String oAuth2Callback) {
        this.oAuth2CallbackUrl = oAuth2Callback;
    }

    /**
     * @return the tokenDirPath
     */
    public String getTokenDirPath() {
        return tokenDirPath;
    }

}
