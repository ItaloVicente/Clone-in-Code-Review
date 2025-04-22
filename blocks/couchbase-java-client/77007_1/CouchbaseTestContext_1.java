        private void loadProperties() {
            if (testProperties != null) {
                return;
            }
            testProperties = new Properties();
            try {
                testProperties.load(CouchbaseTestContext.class.getClassLoader().getResourceAsStream("com.couchbase.client.java.integration.properties"));
            } catch (Exception ex) {
            }
        }

        private boolean isMockEnabled() {
            return Boolean.parseBoolean(testProperties.getProperty("mockEnabled", "false"));
        }

