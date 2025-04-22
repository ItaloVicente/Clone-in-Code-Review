		this.configurationProvider.subscribe(bucketName, this);
		start();
	}

	/**
	 * Get a MemcachedClient based on the REST response from a Membase server.
	 *
	 * This constructor is merely a convenience for situations where the bucket
	 * name is the same as the user name.  This is commonly the case.
	 *
	 * To connect to the "default" special bucket for a given cluster, use an
	 * empty string as the password.
	 *
	 * If a password has not been assigned to the bucket, it is typically an
	 * empty string.
	 *
	 * @param baseList the URI list of one or more servers from the cluster
	 * @param bucketName the bucket name in the cluster you wish to use
	 * @param pwd the password for the bucket
	 * @throws IOException if connections could not be made
	 * @throws ConfigurationException if the configuration provided by the
	 *         server has issues or is not compatible
	 */
	public MemcachedClient(List<URI> baseList,
		String bucketName,
		String pwd) throws IOException, ConfigurationException {
		this(baseList, bucketName, bucketName, pwd);
	}
	public void reconfigure(Bucket bucket) {
		reconfiguring = true;
		try {
			conn.reconfigure(bucket);
		} catch (IllegalArgumentException ex) {
			getLogger().warn("Failed to reconfigure client, staying with previous configuration.", ex);
		} finally {
			reconfiguring = false;
