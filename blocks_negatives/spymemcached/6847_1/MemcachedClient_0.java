    /**
     * Get a MemcachedClient based on the REST response from a Membase server.
     *
     * @param baseList
     * @param bucketName
     * @param usr
     * @param pwd
     * @param isVBucketAware
     * @throws IOException
     * @throws ConfigurationException
     * @deprecated Use the constructor without the isVbucketAware
     */
    public MemcachedClient(final List<URI> baseList,
                           final String bucketName,
                           final String usr, final String pwd,
                           final boolean isVBucketAware) throws IOException, ConfigurationException {
        for (URI bu : baseList) {
            if (!bu.isAbsolute()) {
                throw new IllegalArgumentException("The base URI must be absolute");
            }
        }

        configurationProvider = new ConfigurationProviderHTTP(baseList, usr, pwd);
        Bucket bucket = configurationProvider.getBucketConfiguration(bucketName);
        ConnectionFactoryBuilder cfb = new ConnectionFactoryBuilder();
        if (isVBucketAware) {
            cfb.setFailureMode(FailureMode.Retry)
                    .setProtocol(ConnectionFactoryBuilder.Protocol.BINARY)
                    .setHashAlg(HashAlgorithm.KETAMA_HASH)
                    .setLocatorType(ConnectionFactoryBuilder.Locator.VBUCKET)
                    .setVBucketConfig(bucket.getConfig());
        } else {
            cfb.setFailureMode(FailureMode.Retry)
                    .setProtocol(ConnectionFactoryBuilder.Protocol.BINARY)
                    .setHashAlg(HashAlgorithm.KETAMA_HASH)
                    .setLocatorType(ConnectionFactoryBuilder.Locator.CONSISTENT);

        }
        if (!configurationProvider.getAnonymousAuthBucket().equals(bucketName) && usr != null) {
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
        configurationProvider.subscribe(bucketName, this);
        start();
    }
