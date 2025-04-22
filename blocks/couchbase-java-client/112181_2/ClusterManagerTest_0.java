    @AfterClass
    public static void tearDown() {
        if (couchbaseCluster != null) {
            couchbaseCluster.disconnect();
        }
    }

