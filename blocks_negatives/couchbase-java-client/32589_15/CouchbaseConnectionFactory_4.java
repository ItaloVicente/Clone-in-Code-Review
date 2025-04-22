  /**
   * Updates the stored base list with a new one based on the config.
   *
   * @param config
   */
  public void updateStoredBaseList(Config config) {
    List<String> bucketServers = config.getRestEndpoints();
    if (bucketServers.size() > 0) {
      List<URI> newList = new ArrayList<URI>();
      for (String bucketServer : bucketServers) {
        try {
          newList.add(new URI(bucketServer));
        } catch(URISyntaxException ex) {
          getLogger().warn("Could not add node to updated bucket list because "
            + "of a parsing exception.");
          getLogger().debug("Could not parse list because: " + ex);
        }
      }

      if (nodeListsAreDifferent(storedBaseList, newList)) {
        getLogger().info("Replacing current streaming node list "
          + storedBaseList + " with " + newList);
        potentiallyRandomizeNodeList(newList);
        storedBaseList = newList;
        getConfigurationProvider().updateBaseListFromConfig(newList);
      }
    }
  }

