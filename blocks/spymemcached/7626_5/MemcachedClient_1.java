		this(new BinaryConnectionFactory(), baseList, bucketName, usr, pwd);
	}

	public MemcachedClient(ConnectionFactory cf, final List<URI> baseList,
			final String bucketName, final String usr, final String pwd)
			throws IOException, ConfigurationException {
		ConnectionFactoryBuilder cfb = new ConnectionFactoryBuilder(cf);
