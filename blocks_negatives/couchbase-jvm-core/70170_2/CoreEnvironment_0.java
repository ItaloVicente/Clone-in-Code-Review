    /**
     * Identifies if SSL should be enabled.
     *
     * @return true if SSL is enabled, false otherwise.
     */
    boolean sslEnabled();

    /**
     * Identifies the filepath to the ssl keystore.
     *
     * @return the path to the keystore file.
     */
    String sslKeystoreFile();

    /**
     * The password which is used to protect the keystore.
     *
     * @return the keystore password.
     */
    String sslKeystorePassword();

    /**
     * Allows to directly configure a {@link KeyStore}.
     *
     * @return the keystore to use.
     */
    KeyStore sslKeystore();

