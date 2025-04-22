		if(cf == null) {
			throw new NullPointerException("Connection factory required");
		}
		if(addrs == null) {
			throw new NullPointerException("Server list required");
		}
		if(addrs.isEmpty()) {
			throw new IllegalArgumentException(
				"You must have at least one server to connect to");
		}
		if(cf.getOperationTimeout() <= 0) {
			throw new IllegalArgumentException(
				"Operation timeout must be positive.");
		}
		tcService = new TranscodeService(cf.isDaemon());
		transcoder=cf.getDefaultTranscoder();
		opFact=cf.getOperationFactory();
		assert opFact != null : "Connection factory failed to make op factory";
		conn=cf.createConnection(addrs);
		assert conn != null : "Connection factory failed to make a connection";
		operationTimeout = cf.getOperationTimeout();
		authDescriptor = cf.getAuthDescriptor();
		if(authDescriptor != null) {
			addObserver(this);
		}
		setName("Memcached IO over " + conn);
		setDaemon(cf.isDaemon());
		start();
	}

	/**
	 * Get a MemcachedClient based on the REST response from a Membase server
	 * where the username is different than the bucket name.
	 *
	 * To connect to the "default" special bucket for a given cluster, use an
	 * empty string as the password.
	 *
	 * If a password has not been assigned to the bucket, it is typically an
	 * empty string.
	 *
	 * @param baseList the URI list of one or more servers from the cluster
	 * @param bucketName the bucket name in the cluster you wish to use
	 * @param usr the username for the bucket; this nearly always be the same
	 *        as the bucket name
	 * @param pwd the password for the bucket
	 * @throws IOException if connections could not be made
	 * @throws ConfigurationException if the configuration provided by the
	 *         server has issues or is not compatible
	 */
	public MemcachedClient(final List<URI> baseList, final String bucketName,
		final String usr, final String pwd) throws IOException, ConfigurationException {
		this(new BinaryConnectionFactory(), baseList, bucketName, usr, pwd);
