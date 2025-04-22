   * Get a CouchbaseClient based on the REST response from a Couchbase server
   * where the username is different than the bucket name.
   *
   * To connect to the "default" special bucket for a given cluster, use an
   * empty string as the password.
   *
   * If a password has not been assigned to the bucket, it is typically an empty
   * string.
   *
   * @param baseList the URI list of one or more servers from the cluster
   * @param bucketName the bucket name in the cluster you wish to use
   * @param usr the username for the bucket; this nearly always be the same as
   *          the bucket name
   * @param pwd the password for the bucket
   * @throws IOException if connections could not be made
   * @throws ConfigurationException if the configuration provided by
   *          the server has issues or is not compatible
   */
  public CouchbaseClient(final List<URI> baseList, final String bucketName,
      final String usr, final String pwd) throws IOException {
    this(new CouchbaseConnectionFactory(baseList, bucketName, pwd));
  }

  /**
   * Get a CouchbaseClient based on the REST response from a Couchbase server
   * where the username is different than the bucket name.
