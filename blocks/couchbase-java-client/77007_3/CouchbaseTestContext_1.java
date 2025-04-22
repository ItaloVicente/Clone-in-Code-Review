        private void loadProperties() {
            if (testProperties != null) {
                return;
            }
            testProperties = new Properties();
            try {
                testProperties.load(getClass().getResourceAsStream("/mock.properties"));
            } catch (Exception ex) {
            }
        }

        private boolean isMockEnabled() {
            return Boolean.parseBoolean(testProperties.getProperty("mock.enabled", "false"));
        }

