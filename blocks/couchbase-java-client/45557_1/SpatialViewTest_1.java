    ArrayList<String> versions = new ArrayList<String>(
        client.getVersions().values());
    if (versions.size() > 0) {
      CbTestConfig.Version version = new CbTestConfig.Version(versions.get(0));
      isOldSpatial = version.isOldSpatialAware();
    }

