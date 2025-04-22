   * Get the CouchbaseConnectionFactory set up with the provided parameters.
   * Note that a CouchbaseConnectionFactory requires the failure mode is set
   * to retry, and the locator type is discovered dynamically based on the
   * cluster you are connecting to. As a result, these values will be
   * overridden upon calling this function.
   *
   * @param baseList a list of URI's that will be used to connect to the cluster
   * @param bucketName the name of the bucket to connect to, also used for
   * username
   * @param pwd the password for the bucket
   * @return a CouchbaseConnectionFactory object
   * @throws IOException
