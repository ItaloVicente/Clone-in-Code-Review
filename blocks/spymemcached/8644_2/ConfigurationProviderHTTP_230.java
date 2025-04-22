  private void readPools(String bucketToFind) {
    for (URI baseUri : baseList) {
      try {
        URLConnection baseConnection = urlConnBuilder(null, baseUri);
        String base = readToString(baseConnection);
        if ("".equals(base)) {
          getLogger().warn(
              "Provided URI " + baseUri + " has an empty response... skipping");
          continue;
        }
        Map<String, Pool> pools = this.configurationParser.parseBase(base);
