    };  }


  @Deprecated
  public CouchbaseConnectionFactory buildCouchbaseConnection(
      final List<URI> baseList, final String bucketName, final String usr,
      final String pwd) throws IOException {
    return buildCouchbaseConnection(baseList, bucketName, pwd);
