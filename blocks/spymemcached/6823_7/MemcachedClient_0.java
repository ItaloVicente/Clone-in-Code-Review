    public MemcachedClient(final List<URI> baseList,
                           final String bucketName,
                           final String usr, final String pwd) throws IOException, ConfigurationException {
        for (URI bu : baseList) {
            if (!bu.isAbsolute()) {
                throw new IllegalArgumentException("The base URI must be absolute");
            }
        }

        this.configurationProvider = new ConfigurationProviderHTTP(baseList, usr, pwd);
        Bucket bucket = this.configurationProvider.getBucketConfiguration(bucketName);
	Config config = bucket.getConfig();
        ConnectionFactoryBuilder cfb = new ConnectionFactoryBuilder();
        if (config.getConfigType() == ConfigType.MEMBASE) {
            cfb.setFailureMode(FailureMode.Retry)
                    .setProtocol(ConnectionFactoryBuilder.Protocol.BINARY)
                    .setHashAlg(HashAlgorithm.KETAMA_HASH)
                    .setLocatorType(ConnectionFactoryBuilder.Locator.VBUCKET)
                    .setVBucketConfig(bucket.getConfig());
        } else if (config.getConfigType() == ConfigType.CACHE) {
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
        ConnectionFactory cf = cfb.build();
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

