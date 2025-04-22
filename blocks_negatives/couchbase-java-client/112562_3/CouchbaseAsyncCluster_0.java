    /**
     * Flag which controls the usage of hostnames for seed nodes
     */
    public static final boolean ALLOW_HOSTNAMES_AS_SEED_NODES = Boolean.parseBoolean(
            System.getProperty("com.couchbase.allowHostnamesAsSeedNodes", "false")
    );


