public class MemcachedClient extends SpyObject
	implements MemcachedClientIF, ConnectionObserver {

	protected volatile boolean shuttingDown=false;

	protected final long operationTimeout;

	protected final MemcachedConnection mconn;
	protected final OperationFactory opFact;

	protected final Transcoder<Object> transcoder;

	protected final TranscodeService tcService;

	protected final AuthDescriptor authDescriptor;

	protected final ConnectionFactory connFactory;

	protected final AuthThreadMonitor authMonitor = new AuthThreadMonitor();

	/**
	 * Get a memcache client operating on the specified memcached locations.
	 *
	 * @param ia the memcached locations
	 * @throws IOException if connections cannot be established
	 */
	public MemcachedClient(InetSocketAddress... ia) throws IOException {
		this(new DefaultConnectionFactory(), Arrays.asList(ia));
	}

	/**
	 * Get a memcache client over the specified memcached locations.
	 *
	 * @param addrs the socket addrs
	 * @throws IOException if connections cannot be established
	 */
	public MemcachedClient(List<InetSocketAddress> addrs)
		throws IOException {
		this(new DefaultConnectionFactory(), addrs);
	}

	/**
	 * Get a memcache client over the specified memcached locations.
	 *
	 * @param cf the connection factory to configure connections for this client
	 * @param addrs the socket addresses
	 * @throws IOException if connections cannot be established
	 */
	public MemcachedClient(ConnectionFactory cf, List<InetSocketAddress> addrs)
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
		connFactory = cf;
		tcService = new TranscodeService(cf.isDaemon());
		transcoder=cf.getDefaultTranscoder();
		opFact=cf.getOperationFactory();
		assert opFact != null : "Connection factory failed to make op factory";
		mconn=cf.createConnection(addrs);
		assert mconn != null : "Connection factory failed to make a connection";
		operationTimeout = cf.getOperationTimeout();
		authDescriptor = cf.getAuthDescriptor();
		if(authDescriptor != null) {
			addObserver(this);
		}
	}

	/**
	 * Get the addresses of available servers.
	 *
	 * <p>
	 * This is based on a snapshot in time so shouldn't be considered
	 * completely accurate, but is a useful for getting a feel for what's
	 * working and what's not working.
	 * </p>
	 *
	 * @return point-in-time view of currently available servers
	 */
	public Collection<SocketAddress> getAvailableServers() {
		ArrayList<SocketAddress> rv=new ArrayList<SocketAddress>();
		for(MemcachedNode node : mconn.getLocator().getAll()) {
			if(node.isActive()) {
				rv.add(node.getSocketAddress());
			}
		}
		return rv;
	}

	/**
	 * Get the addresses of unavailable servers.
	 *
	 * <p>
	 * This is based on a snapshot in time so shouldn't be considered
	 * completely accurate, but is a useful for getting a feel for what's
	 * working and what's not working.
	 * </p>
	 *
	 * @return point-in-time view of currently available servers
	 */
	public Collection<SocketAddress> getUnavailableServers() {
		ArrayList<SocketAddress> rv=new ArrayList<SocketAddress>();
		for(MemcachedNode node : mconn.getLocator().getAll()) {
			if(!node.isActive()) {
				rv.add(node.getSocketAddress());
			}
		}
		return rv;
	}

	/**
	 * Get a read-only wrapper around the node locator wrapping this instance.
	 *
	 * @return this instance's NodeLocator
	 */
	public NodeLocator getNodeLocator() {
		return mconn.getLocator().getReadonlyCopy();
	}

	/**
	 * Get the default transcoder that's in use.
	 *
	 * @return this instance's Transcoder
	 */
	public Transcoder<Object> getTranscoder() {
		return transcoder;
	}

	private void validateKey(String key) {
		byte[] keyBytes=KeyUtil.getKeyBytes(key);
		if(keyBytes.length > MAX_KEY_LENGTH) {
			throw new IllegalArgumentException("Key is too long (maxlen = "
					+ MAX_KEY_LENGTH + ")");
		}
		if(keyBytes.length == 0) {
			throw new IllegalArgumentException(
				"Key must contain at least one character.");
		}
		for(byte b : keyBytes) {
			if(b == ' ' || b == '\n' || b == '\r' || b == 0) {
				throw new IllegalArgumentException(
					"Key contains invalid characters:  ``" + key + "''");
			}
		}
	}

	/**
	 * (internal use) Add a raw operation to a numbered connection.
	 * This method is exposed for testing.
	 *
	 * @param which server number
	 * @param op the operation to perform
	 * @return the Operation
	 */
	Operation addOp(final String key, final Operation op) {
		validateKey(key);
		mconn.checkState();
		mconn.addOperation(key, op);
		return op;
	}

	CountDownLatch broadcastOp(final BroadcastOpFactory of) {
		return broadcastOp(of, mconn.getLocator().getAll(), true);
	}

	CountDownLatch broadcastOp(final BroadcastOpFactory of,
			Collection<MemcachedNode> nodes) {
		return broadcastOp(of, nodes, true);
	}

	private CountDownLatch broadcastOp(BroadcastOpFactory of,
			Collection<MemcachedNode> nodes,
			boolean checkShuttingDown) {
		if(checkShuttingDown && shuttingDown) {
			throw new IllegalStateException("Shutting down");
		}
		return mconn.broadcastOperation(of, nodes);
	}

	private <T> OperationFuture<Boolean> asyncStore(StoreType storeType, String key,
						   int exp, T value, Transcoder<T> tc) {
		CachedData co=tc.encode(value);
		final CountDownLatch latch=new CountDownLatch(1);
		final OperationFuture<Boolean> rv=new OperationFuture<Boolean>(key, latch,
				operationTimeout);
		Operation op=opFact.store(storeType, key, co.getFlags(),
				exp, co.getData(), new OperationCallback() {
					public void receivedStatus(OperationStatus val) {
						rv.set(val.isSuccess(), val);
					}
					public void complete() {
						latch.countDown();
					}});
		rv.setOperation(op);
		addOp(key, op);
		return rv;
	}

	private OperationFuture<Boolean> asyncStore(StoreType storeType,
			String key, int exp, Object value) {
		return asyncStore(storeType, key, exp, value, transcoder);
	}

	private <T> OperationFuture<Boolean> asyncCat(
			ConcatenationType catType, long cas, String key,
			T value, Transcoder<T> tc) {
		CachedData co=tc.encode(value);
		final CountDownLatch latch=new CountDownLatch(1);
		final OperationFuture<Boolean> rv=new OperationFuture<Boolean>(key, latch,
				operationTimeout);
		Operation op=opFact.cat(catType, cas, key, co.getData(),
				new OperationCallback() {
			public void receivedStatus(OperationStatus val) {
				rv.set(val.isSuccess(), val);
			}
			public void complete() {
				latch.countDown();
			}});
		rv.setOperation(op);
		addOp(key, op);
		return rv;
	}

	/**
	 * Touch the given key to reset its expiration time with the default
	 * transcoder.
	 *
	 * @param key the key to fetch
	 * @param exp the new expiration to set for the given key
	 * @return a future that will hold the return value of whether or not
	 * the fetch succeeded
	 * @throws IllegalStateException in the rare circumstance where queue
	 *         is too full to accept any more requests
	 */
	public <T> OperationFuture<Boolean> touch(final String key, final int exp) {
		return touch(key, exp, transcoder);
	}

	/**
	 * Touch the given key to reset its expiration time.
	 *
	 * @param key the key to fetch
	 * @param exp the new expiration to set for the given key
	 * @param tc the transcoder to serialize and unserialize value
	 * @return a future that will hold the return value of whether or not
	 * the fetch succeeded
	 * @throws IllegalStateException in the rare circumstance where queue
	 *         is too full to accept any more requests
	 */
	public <T> OperationFuture<Boolean> touch(final String key, final int exp,
			final Transcoder<T> tc) {
		final CountDownLatch latch=new CountDownLatch(1);
		final OperationFuture<Boolean> rv=new OperationFuture<Boolean>(key, latch,
				operationTimeout);

		Operation op=opFact.touch(key, exp, new OperationCallback() {
			public void receivedStatus(OperationStatus status) {
				rv.set(status.isSuccess(), status);
		}
			public void complete() {
				latch.countDown();
			}});
		rv.setOperation(op);
		addOp(key, op);
		return rv;
	}

	/**
	 * Append to an existing value in the cache.
	 *
	 * <p>Note that the return will be false any time a mutation has not
	 * occurred.</p>
	 *
	 * @param cas cas identifier (ignored in the ascii protocol)
	 * @param key the key to whose value will be appended
	 * @param val the value to append
	 * @return a future indicating success, false if there was no change
	 *         to the value
	 * @throws IllegalStateException in the rare circumstance where queue
	 *         is too full to accept any more requests
	 */
	public OperationFuture<Boolean> append(long cas, String key, Object val) {
		return append(cas, key, val, transcoder);
	}

	/**
	 * Append to an existing value in the cache.
	 *
	 * <p>Note that the return will be false any time a mutation has not
	 * occurred.</p>
	 *
	 * @param <T>
	 * @param cas cas identifier (ignored in the ascii protocol)
	 * @param key the key to whose value will be appended
	 * @param val the value to append
	 * @param tc the transcoder to serialize and unserialize the value
	 * @return a future indicating success
	 * @throws IllegalStateException in the rare circumstance where queue
	 *         is too full to accept any more requests
	 */
	public <T> OperationFuture<Boolean> append(long cas, String key, T val,
			Transcoder<T> tc) {
		return asyncCat(ConcatenationType.append, cas, key, val, tc);
	}

	/**
	 * Prepend to an existing value in the cache.
	 *
	 * <p>Note that the return will be false any time a mutation has not
	 * occurred.</p>
	 *
	 * @param cas cas identifier (ignored in the ascii protocol)
	 * @param key the key to whose value will be prepended
	 * @param val the value to append
	 * @return a future indicating success
	 * @throws IllegalStateException in the rare circumstance where queue
	 *         is too full to accept any more requests
	 */
	public OperationFuture<Boolean> prepend(long cas, String key, Object val) {
		return prepend(cas, key, val, transcoder);
	}

	/**
	 * Prepend to an existing value in the cache.
	 *
	 * <p>Note that the return will be false any time a mutation has not
	 * occurred.</p>
	 *
	 * @param <T>
	 * @param cas cas identifier (ignored in the ascii protocol)
	 * @param key the key to whose value will be prepended
	 * @param val the value to append
	 * @param tc the transcoder to serialize and unserialize the value
	 * @return a future indicating success
	 * @throws IllegalStateException in the rare circumstance where queue
	 *         is too full to accept any more requests
	 */
	public <T> OperationFuture<Boolean> prepend(long cas, String key, T val,
			Transcoder<T> tc) {
		return asyncCat(ConcatenationType.prepend, cas, key, val, tc);
	}

	/**
     * Asynchronous CAS operation.
     *
     * @param <T>
     * @param key the key
     * @param casId the CAS identifier (from a gets operation)
     * @param value the new value
     * @param tc the transcoder to serialize and unserialize the value
     * @return a future that will indicate the status of the CAS
     * @throws IllegalStateException in the rare circumstance where queue
     *         is too full to accept any more requests
     */
    public <T> Future<CASResponse> asyncCAS(String key, long casId, T value,
            Transcoder<T> tc) {
        return asyncCAS(key, casId, 0, value, tc);
	}

	/**
	 * Asynchronous CAS operation.
	 *
	 * @param <T>
	 * @param key the key
	 * @param casId the CAS identifier (from a gets operation)
	 * @param exp the expiration of this object
	 * @param value the new value
	 * @param tc the transcoder to serialize and unserialize the value
	 * @return a future that will indicate the status of the CAS
	 * @throws IllegalStateException in the rare circumstance where queue
	 *         is too full to accept any more requests
	 */
	public <T> Future<CASResponse> asyncCAS(String key, long casId, int exp, T value,
			Transcoder<T> tc) {
		CachedData co=tc.encode(value);
		final CountDownLatch latch=new CountDownLatch(1);
		final OperationFuture<CASResponse> rv=new OperationFuture<CASResponse>(
				key, latch, operationTimeout);
		Operation op=opFact.cas(StoreType.set, key, casId, co.getFlags(), exp,
				co.getData(), new OperationCallback() {
					public void receivedStatus(OperationStatus val) {
						if(val instanceof CASOperationStatus) {
							rv.set(((CASOperationStatus)val).getCASResponse(), val);
						} else if(val instanceof CancelledOperationStatus) {
						} else {
							throw new RuntimeException(
								"Unhandled state: " + val);
						}
					}
					public void complete() {
						latch.countDown();
					}});
		rv.setOperation(op);
		addOp(key, op);
		return rv;
	}

	/**
	 * Asynchronous CAS operation using the default transcoder.
	 *
	 * @param key the key
	 * @param casId the CAS identifier (from a gets operation)
	 * @param value the new value
	 * @return a future that will indicate the status of the CAS
	 * @throws IllegalStateException in the rare circumstance where queue
	 *         is too full to accept any more requests
	 */
	public Future<CASResponse> asyncCAS(String key, long casId, Object value) {
		return asyncCAS(key, casId, value, transcoder);
	}

	/**
     * Perform a synchronous CAS operation.
     *
     * @param <T>
     * @param key the key
     * @param casId the CAS identifier (from a gets operation)
     * @param value the new value
     * @param tc the transcoder to serialize and unserialize the value
     * @return a CASResponse
     * @throws OperationTimeoutException if global operation timeout is
     *         exceeded
     * @throws IllegalStateException in the rare circumstance where queue
     *         is too full to accept any more requests
     */
    public <T> CASResponse cas(String key, long casId, T value,
            Transcoder<T> tc) {
        return cas(key, casId, 0, value, tc);
