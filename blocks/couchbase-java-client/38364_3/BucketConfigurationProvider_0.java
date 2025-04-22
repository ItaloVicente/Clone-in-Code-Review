    try {
        Bucket config = configurationParser.parseBucket(appliedConfig);
        setConfig(config);
    } catch(Exception ex) {
        getLogger().warn("Could not parse config, retrying bootstrap.", ex);
        connection.shutdown();
        return false;
    }

