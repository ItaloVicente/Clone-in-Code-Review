	public CouchbaseConnectionFactory(List<URI> baseList, String bucketName,
			String usr, String pwd) throws IOException {
		super(baseList, bucketName, usr, pwd);
	}

	public CouchbaseNode createCouchDBNode(InetSocketAddress addr,
			AsyncConnectionManager connMgr) {

		return new CouchbaseNode(addr, connMgr,
				new LinkedBlockingQueue<HttpOperation>(opQueueLen),
				getOpQueueMaxBlockTime(), getOperationTimeout());
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * net.spy.memcached.ConnectionFactory#createMemcachedConnection(java.util
	 * .List)
	 */
	public MemcachedConnection createMemcachedConnection(
			List<InetSocketAddress> addrs) throws IOException {
		return new MemcachedConnection(getReadBufSize(), this, addrs,
				getInitialObservers(), getFailureMode(), getOperationFactory());
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * net.spy.memcached.ConnectionFactory#createCouchDBConnection(java.util
	 * .List)
	 */
	public CouchbaseConnection createCouchDBConnection(
			List<InetSocketAddress> addrs) throws IOException {
		return new CouchbaseConnection(this, addrs, getInitialObservers());
	}
