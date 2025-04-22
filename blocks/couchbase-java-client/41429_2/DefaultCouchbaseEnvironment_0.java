    public static String PACKAGE_NAME_AND_VERSION = "couchbase-java-client";

    static {
        try {
            Class<CouchbaseCluster> facadeClass = CouchbaseCluster.class;
            if (facadeClass == null) {
                throw new IllegalStateException("Could not locate ClusterFacade");
            }

            Package pkg = Package.getPackage("com.couchbase.client.java");
            String version = pkg.getSpecificationVersion();
            String gitVersion = pkg.getImplementationVersion();
            PACKAGE_NAME_AND_VERSION = String.format("couchbase-java-client/%s (git: %s)",
                version == null ? "unknown" : version, gitVersion == null ? "unknown" : gitVersion);

            USER_AGENT = String.format("%s (%s/%s %s; %s %s)",
                PACKAGE_NAME_AND_VERSION,
                System.getProperty("os.name"),
                System.getProperty("os.version"),
                System.getProperty("os.arch"),
                System.getProperty("java.vm.name"),
                System.getProperty("java.runtime.version")
            );
        } catch (Exception ex) {
            LOGGER.info("Could not set up user agent and packages, defaulting.", ex);
        }
    }
