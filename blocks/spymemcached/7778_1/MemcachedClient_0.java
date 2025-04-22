		this (baseList, bucketName, usr, pwd, true);
	}

	protected MemcachedClient(final List<URI> baseList, final String bucketName,
			final String usr, final String pwd, boolean startIOThread)
			throws IOException, ConfigurationException {
