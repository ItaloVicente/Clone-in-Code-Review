
    private static Properties loadProperties() {
        if (mockProperties != null) {
            return mockProperties;
        }
        mockProperties = new Properties();
        try {
            mockProperties.load(CouchbaseTestContext.class.getResourceAsStream("/mock.properties"));
        } catch (Exception ex) {
        }
        return mockProperties;
    }

    public static boolean isMockEnabled() {
        return Boolean.parseBoolean(mockProperties.getProperty("useMock", "true"));
    }
