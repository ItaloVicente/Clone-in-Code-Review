        boolean analyticsEnabled = Boolean.parseBoolean(
            System.getProperty("com.couchbase.analyticsEnabled", "false")
        );
        int analyticsPort = Integer.parseInt(
            System.getProperty("com.couchbase.analyticsPort", "8095")
        );
        int analyticsSslPort = Integer.parseInt(
            System.getProperty("com.couchbase.analyticsSslPort", "18095")
        );
        if (analyticsEnabled) {
            ports.put(ServiceType.ANALYTICS, analyticsPort);
            sslPorts.put(ServiceType.ANALYTICS, analyticsSslPort);
        }

