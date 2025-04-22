  public CouchbaseConnectionFactory() {
    String nodes = CouchbaseProperties.getProperty("nodes");
    String bucket =  CouchbaseProperties.getProperty("bucket");
    String password = CouchbaseProperties.getProperty("password");

    if (nodes == null) {
      throw new IllegalArgumentException("System property cbclient.nodes "
        + "not set or empty");
    }
    if (bucket == null) {
      throw new IllegalArgumentException("System property cbclient.bucket "
        + "not set or empty");
    }
    if (password == null) {
      throw new IllegalArgumentException("System property cbclient.password "
        + "not set or empty");
    }

    List<URI> baseList = new ArrayList<URI>();
    String[] nodeList = nodes.split(";");
    for (String node : nodeList) {
      try {
        baseList.add(new URI(node));
      } catch (Exception e) {
        throw new IllegalArgumentException("Could not parse node list into "
          + " URI format.");
      }
    }

    initialize(baseList, bucket, password);
  }

