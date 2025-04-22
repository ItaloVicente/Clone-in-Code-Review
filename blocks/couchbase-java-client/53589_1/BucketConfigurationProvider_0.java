      try {
        if (binaryConnection.get() == null) {
          bootstrap();
        } else {
          try {
            List<String> configs = getConfigsFromBinaryConnection(binaryConnection.get());
            if (configs.isEmpty()) {
              bootstrap();
              return;
            }
            String appliedConfig = binaryConnection.get().replaceConfigWildcards(
                configs.get(0));
            Bucket config = configurationParser.parseBucket(appliedConfig);
            setConfig(config);
          } catch(Exception ex) {
            getLogger().info("Could not load config from existing "
                + "connection, rerunning bootstrap.", ex);
