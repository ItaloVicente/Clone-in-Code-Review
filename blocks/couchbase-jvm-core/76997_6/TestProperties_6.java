        Properties properties = new Properties();
        try {
            properties.load(TestProperties.class.getClassLoader().getResourceAsStream("com.couchbase.client.core.integration.properties"));
        } catch (Exception ex) {
        }
        seedNode = properties.getProperty("seedNode", "127.0.0.1");
        bucket = properties.getProperty("bucket", "default");
        username = properties.getProperty("username", "default");
        password = properties.getProperty("password", "");
        adminUser = properties.getProperty("adminUser", "Administrator");
        adminPassword = properties.getProperty("adminPassword", "password");
        mockNodeCount = Integer.parseInt(properties.getProperty("mockNodeCount", "1"));
        mockReplicaCount = Integer.parseInt(properties.getProperty("mockReplicaCount", "1"));
        bucketType = properties.getProperty("mockBucketType", "couchbase").equalsIgnoreCase("couchbase") ? COUCHBASE : MEMCACHED;
        createMock();
