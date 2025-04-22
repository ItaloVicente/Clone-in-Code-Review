public class TapConnectionProvider extends SpyObject implements ConnectionObserver, Reconfigurable {

	private volatile boolean shuttingDown=false;

	private final MemcachedConnection conn;

	final OperationFactory opFact;

	final Transcoder<Object> transcoder;

	final TranscodeService tcService;

	final AuthDescriptor authDescriptor;

	private final AuthThreadMonitor authMonitor = new AuthThreadMonitor();

	private ConfigurationProvider configurationProvider;

	/**
	 * Get a memcache client operating on the specified memcached locations.
	 *
	 * @param ia the memcached locations
	 * @throws IOException if connections cannot be established
	 */
	public TapConnectionProvider(InetSocketAddress... ia) throws IOException {
		this(new BinaryConnectionFactory(), Arrays.asList(ia));
	}

	/**
	 * Get a memcache client over the specified memcached locations.
	 *
	 * @param addrs the socket addrs
	 * @throws IOException if connections cannot be established
	 */
	public TapConnectionProvider(List<InetSocketAddress> addrs)
		throws IOException {
		this(new BinaryConnectionFactory(), addrs);
	}

	/**
	 * Get a memcache client over the specified memcached locations.
	 *
	 * @param cf the connection factory to configure connections for this client
	 * @param addrs the socket addresses
	 * @throws IOException if connections cannot be established
	 */
	private TapConnectionProvider(ConnectionFactory cf, List<InetSocketAddress> addrs)
		throws IOException {
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
		authDescriptor = cf.getAuthDescriptor();
		if(authDescriptor != null) {
			addObserver(this);
		}
	}

	/**
	 * Get a MemcachedClient based on the REST response from a Membase server.
	 *
	 * @param baseList
	 * @param bucketName
	 * @param usr
	 * @param pwd
	 * @throws IOException
	 * @throws ConfigurationException
	 */
	public TapConnectionProvider(final List<URI> baseList,
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
		} else if (config.getConfigType() == ConfigType.MEMCACHE) {
			cfb.setFailureMode(FailureMode.Redistribute)
					.setProtocol(ConnectionFactoryBuilder.Protocol.BINARY)
					.setHashAlg(HashAlgorithm.KETAMA_HASH)
					.setLocatorType(ConnectionFactoryBuilder.Locator.CONSISTENT)
					.setShouldOptimize(false);
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
		authDescriptor = cf.getAuthDescriptor();
		if(authDescriptor != null) {
			addObserver(this);
		}
		this.configurationProvider.subscribe(bucketName, this);
	}

	Operation addOp(final Operation op) {
		conn.checkState();
		conn.addOperation("", op);
		return op;
	}

	/**
	 * Add a connection observer.
	 *
	 * If connections are already established, your observer will be called
	 * with the address and -1.
	 *
	 * @param obs the ConnectionObserver you wish to add
	 * @return true if the observer was added.
	 */
	public boolean addObserver(ConnectionObserver obs) {
		boolean rv = conn.addObserver(obs);
		if(rv) {
			for(MemcachedNode node : conn.getLocator().getAll()) {
				if(node.isActive()) {
					obs.connectionEstablished(node.getSocketAddress(), -1);
				}
			}
		}
		return rv;
	}

	/**
	 * Remove a connection observer.
	 *
	 * @param obs the ConnectionObserver you wish to add
	 * @return true if the observer existed, but no longer does
	 */
	public boolean removeObserver(ConnectionObserver obs) {
		return conn.removeObserver(obs);
	}

	public void connectionEstablished(SocketAddress sa, int reconnectCount) {
		if(authDescriptor != null) {
			if (authDescriptor.authThresholdReached()) {
				this.shutdown();
			} else {
				authMonitor.authConnection(conn, opFact, authDescriptor, findNode(sa));
			}
		}
	}

	private MemcachedNode findNode(SocketAddress sa) {
		MemcachedNode node = null;
		for(MemcachedNode n : conn.getLocator().getAll()) {
			if(n.getSocketAddress().equals(sa)) {
				node = n;
			}
		}
		assert node != null : "Couldn't find node connected to " + sa;
		return node;
	}

	public void connectionLost(SocketAddress sa) {
	}

    public void reconfigure(Bucket bucket) {
        this.conn.reconfigure(bucket);
