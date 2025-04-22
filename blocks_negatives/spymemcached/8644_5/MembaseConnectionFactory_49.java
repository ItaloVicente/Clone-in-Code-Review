	/**
	 * Default failure mode.
	 */
	public static final FailureMode DEFAULT_FAILURE_MODE =
		FailureMode.Retry;

	/**
	 * Default hash algorithm.
	 */
	public static final HashAlgorithm DEFAULT_HASH = HashAlgorithm.KETAMA_HASH;

	/**
	 * Maximum length of the operation queue returned by this connection
	 * factory.
	 */
	public static final int DEFAULT_OP_QUEUE_LEN=16384;

	private final Locator locator;
	private final AuthDescriptor ad;
	private final ConfigurationProvider configurationProvider;
	private final Config vbConfig;
	private final String bucketName;

	public MembaseConnectionFactory(final List<URI> baseList,
			final String bucketName, final String usr, final String pwd) throws IOException {
		for (URI bu : baseList) {
			if (!bu.isAbsolute()) {
				throw new IllegalArgumentException("The base URI must be absolute");
			}
		}
		this.bucketName = bucketName;
		this.configurationProvider = new ConfigurationProviderHTTP(baseList, usr, pwd);
		Bucket bucket = this.configurationProvider.getBucketConfiguration(bucketName);
		Config config = bucket.getConfig();

		if (config.getConfigType() == ConfigType.MEMBASE) {
			locator = Locator.VBUCKET;
			vbConfig = bucket.getConfig();
		} else if (config.getConfigType() == ConfigType.MEMCACHE) {
			locator = Locator.CONSISTENT;
			vbConfig = null;
		} else {
			locator = null;
			vbConfig = null;
			throw new ConfigurationException("Bucket type not supported or JSON response unexpected");
		}

		if (!this.configurationProvider.getAnonymousAuthBucket().equals(bucketName) && usr != null) {
			ad = new AuthDescriptor(new String[]{"PLAIN"},new PlainCallbackHandler(usr, pwd));
		} else {
			ad = null;
		}
	}

	@Override
	public NodeLocator createLocator(List<MemcachedNode> nodes) {
		switch(locator) {
			case CONSISTENT:
				return new KetamaNodeLocator(nodes, getHashAlg());
            case VBUCKET:
                return new VBucketNodeLocator(nodes, getVBucketConfig());
			default: throw new IllegalStateException(
					"Unhandled locator type: " + locator);
		}
	}

	public AuthDescriptor getAuthDescriptor() {
		return ad;
	}

	public Config getVBucketConfig() {
		return vbConfig;
	}

	public String getBucket() {
		return  bucketName;
	}

	public ConfigurationProvider getConfigurationProvider() {
		return configurationProvider;
	}

	public Locator getLocator() {
		return locator;
	}
