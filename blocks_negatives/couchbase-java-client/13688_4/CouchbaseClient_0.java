  /**
   * Get a CouchbaseClient based on the REST response from a Couchbase server
   * where the username is different than the bucket name.
   *
   * Note that when specifying a ConnectionFactory you must specify a
   * BinaryConnectionFactory. Also the ConnectionFactory's protocol and locator
   * values are always overwritten. The protocol will always be binary and the
   * locator will be chosen based on the bucket type you are connecting to.
   *
   * To connect to the "default" special bucket for a given cluster, use an
   * empty string as the password.
   *
   * If a password has not been assigned to the bucket, it is typically an empty
   * string.
   *
   * @param cf the ConnectionFactory to use to create connections
   * @throws IOException if connections could not be made
   * @throws ConfigurationException if the configuration provided by the server
   *           has issues or is not compatible
   */
  public CouchbaseClient(CouchbaseConnectionFactory cf) throws IOException {
    this(cf, true);
  }

