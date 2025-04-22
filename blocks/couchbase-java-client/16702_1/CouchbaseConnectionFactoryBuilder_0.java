  public CouchbaseConnectionFactory buildCouchbaseConnection(
    final List<URI> baseList, final String bucketName, final String pwd)
    throws IOException {
    return buildCouchbaseConnection(baseList, bucketName, bucketName, pwd);
  }

