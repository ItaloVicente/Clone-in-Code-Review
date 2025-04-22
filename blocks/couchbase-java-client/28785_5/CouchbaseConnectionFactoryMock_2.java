    this.order = order;
  }

  public CouchbaseConnectionFactoryMock(final List<URI> baseList,
    final String bucketName, String password, ConfigurationProvider cp)
    throws IOException {
    this(baseList, bucketName, password, cp, DEFAULT_STREAMING_NODE_ORDER);
