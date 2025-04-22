          String appliedConfig = binaryConnection.get().replaceConfigWildcards(
            configs.get(0));
          Bucket config = configurationParser.parseBucket(appliedConfig);
          setConfig(config);
        } catch(Exception ex) {
          getLogger().info("Could not load config from existing "
            + "connection, rerunning bootstrap.", ex);
          bootstrap();
