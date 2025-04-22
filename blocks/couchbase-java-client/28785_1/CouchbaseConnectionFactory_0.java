  public void updateStoredBaseList(Config config) {
    List<String> bucketServers = config.getServers();
    if (bucketServers.size() > 0) {
      List<URI> newList = new ArrayList<URI>();
      for (String bucketServer : bucketServers) {
        String hostname = bucketServer.split(":")[0];
        try {
          newList.add(new URI("http://" + hostname + ":8091/pools"));
        } catch(URISyntaxException ex) {
          getLogger().info("Could not add node to updated bucket list because "
            + "of a parsing exception.");
        }
      }

      if (nodeListsAreDifferent(storedBaseList, newList)) {
        getLogger().info("Replacing current streaming node list "
          + storedBaseList + " with " + newList);
        storedBaseList = newList;
        getConfigurationProvider().updateBaseListFromConfig(newList);
      }
    }
  }

  private boolean nodeListsAreDifferent(List<URI> left, List<URI> right) {
    if (left.size() != right.size()) {
      return true;
    }

    for (URI uri : left) {
      if (!right.contains(uri)) {
        return true;
      }
    }
    return false;
  }

