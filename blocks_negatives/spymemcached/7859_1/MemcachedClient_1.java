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
	}

	/**
	 * Get a MemcachedClient based on the REST response from a Membase server
	 * where the username is different than the bucket name.
	 *
	 * Note that when specifying a ConnectionFactory you must specify a
	 * BinaryConnectionFactory. Also the ConnectionFactory's protocol
	 * and locator values are always overwritten. The protocol will always
	 * be binary and the locator will be chosen based on the bucket type you
	 * are connecting to.
	 *
	 * To connect to the "default" special bucket for a given cluster, use an
	 * empty string as the password.
	 *
	 * If a password has not been assigned to the bucket, it is typically an
	 * empty string.
	 *
	 * @param cf the ConnectionFactory to use to create connections
	 * @param baseList the URI list of one or more servers from the cluster
	 * @param bucketName the bucket name in the cluster you wish to use
	 * @param usr the username for the bucket; this nearly always be the same
	 *        as the bucket name
	 * @param pwd the password for the bucket
	 * @throws IOException if connections could not be made
	 * @throws ConfigurationException if the configuration provided by the
	 *         server has issues or is not compatible
	 */
	public MemcachedClient(ConnectionFactory cf, final List<URI> baseList,
			final String bucketName, final String usr, final String pwd)
			throws IOException, ConfigurationException {
		ConnectionFactoryBuilder cfb = new ConnectionFactoryBuilder(cf);
		for (URI bu : baseList) {
			if (!bu.isAbsolute()) {
				throw new IllegalArgumentException("The base URI must be absolute");
			}
		}
		this.configurationProvider = new ConfigurationProviderHTTP(baseList, usr, pwd);
		Bucket bucket = this.configurationProvider.getBucketConfiguration(bucketName);
		Config config = bucket.getConfig();

		if (cf != null && !(cf instanceof BinaryConnectionFactory)) {
			throw new IllegalArgumentException("ConnectionFactory must be of type " +
					"BinaryConnectionFactory");
		}

		if (config.getConfigType() == ConfigType.MEMBASE) {
			cfb.setFailureMode(FailureMode.Retry)
				.setProtocol(ConnectionFactoryBuilder.Protocol.BINARY)
				.setHashAlg(HashAlgorithm.KETAMA_HASH)
				.setLocatorType(ConnectionFactoryBuilder.Locator.VBUCKET)
				.setVBucketConfig(bucket.getConfig());
		} else if (config.getConfigType() == ConfigType.MEMCACHE) {
			cfb.setFailureMode(FailureMode.Retry)
				.setProtocol(ConnectionFactoryBuilder.Protocol.BINARY)
				.setHashAlg(HashAlgorithm.KETAMA_HASH)
				.setLocatorType(ConnectionFactoryBuilder.Locator.CONSISTENT);
		} else {
			throw new ConfigurationException("Bucket type not supported or JSON response unexpected");
		}

		if (!this.configurationProvider.getAnonymousAuthBucket().equals(bucketName) && usr != null) {
			AuthDescriptor ad = new AuthDescriptor(new String[]{"PLAIN"},
				new PlainCallbackHandler(usr, pwd));
			cfb.setAuthDescriptor(ad);
		}

		cf = cfb.build();

		List<InetSocketAddress> addrs = AddrUtil.getAddresses(bucket.getConfig().getServers());
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
		}
	}

