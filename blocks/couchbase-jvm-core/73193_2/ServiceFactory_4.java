    private static final CouchbaseLogger LOGGER = CouchbaseLoggerFactory.getInstance(ServiceFactory.class);

    private static final boolean FORCE_OLD_SERVICES;

    static {
        FORCE_OLD_SERVICES = Boolean.parseBoolean(
                System.getProperty("com.couchbase.forceOldServices", "false")
        );

        if (FORCE_OLD_SERVICES) {
            LOGGER.info("Old Service override is enabled!");
        } else {
            LOGGER.debug("New Services are enabled");
        }
    }

