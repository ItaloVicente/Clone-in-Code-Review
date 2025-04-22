	/**
	 * Perform a synchronous CAS operation.
	 *
	 * @param <T>
	 * @param key the key
	 * @param casId the CAS identifier (from a gets operation)
	 * @param exp the expiration of this object
	 * @param value the new value
	 * @param tc the transcoder to serialize and unserialize the value
	 * @return a CASResponse
	 * @throws OperationTimeoutException if global operation timeout is
	 *         exceeded
	 * @throws IllegalStateException in the rare circumstance where queue
	 *         is too full to accept any more requests
	 */
	public <T> CASResponse cas(String key, long casId, int exp, T value,
			Transcoder<T> tc) {
		try {
			return asyncCAS(key, casId, exp, value, tc).get(operationTimeout,
					TimeUnit.MILLISECONDS);
		} catch (InterruptedException e) {
			throw new RuntimeException("Interrupted waiting for value", e);
		} catch (ExecutionException e) {
			throw new RuntimeException("Exception waiting for value", e);
		} catch (TimeoutException e) {
			throw new OperationTimeoutException("Timeout waiting for value", e);
		}
	}

	/**
	 * Perform a synchronous CAS operation with the default transcoder.
	 *
	 * @param key the key
	 * @param casId the CAS identifier (from a gets operation)
	 * @param value the new value
	 * @return a CASResponse
	 * @throws OperationTimeoutException if the global operation timeout is
	 *		   exceeded
	 * @throws IllegalStateException in the rare circumstance where queue
	 *         is too full to accept any more requests
	 */
	public CASResponse cas(String key, long casId, Object value) {
		return cas(key, casId, value, transcoder);
	}

	/**
	 * Add an object to the cache iff it does not exist already.
	 *
	 * <p>
	 * The <code>exp</code> value is passed along to memcached exactly as
	 * given, and will be processed per the memcached protocol specification:
	 * </p>
	 *
	 * <p>Note that the return will be false any time a mutation has not
	 * occurred.</p>
	 *
	 * <blockquote>
	 * <p>
	 * The actual value sent may either be
	 * Unix time (number of seconds since January 1, 1970, as a 32-bit
	 * value), or a number of seconds starting from current time. In the
	 * latter case, this number of seconds may not exceed 60*60*24*30 (number
	 * of seconds in 30 days); if the number sent by a client is larger than
	 * that, the server will consider it to be real Unix time value rather
	 * than an offset from current time.
	 * </p>
	 * </blockquote>
	 *
	 * @param <T>
	 * @param key the key under which this object should be added.
	 * @param exp the expiration of this object
	 * @param o the object to store
	 * @param tc the transcoder to serialize and unserialize the value
	 * @return a future representing the processing of this operation
	 * @throws IllegalStateException in the rare circumstance where queue
	 *         is too full to accept any more requests
	 */
	public <T> OperationFuture<Boolean> add(String key, int exp, T o, Transcoder<T> tc) {
		return asyncStore(StoreType.add, key, exp, o, tc);
	}

	/**
	 * Add an object to the cache (using the default transcoder)
	 * iff it does not exist already.
	 *
	 * <p>
	 * The <code>exp</code> value is passed along to memcached exactly as
	 * given, and will be processed per the memcached protocol specification:
	 * </p>
	 *
	 * <p>Note that the return will be false any time a mutation has not
	 * occurred.</p>
	 *
	 * <blockquote>
	 * <p>
	 * The actual value sent may either be
	 * Unix time (number of seconds since January 1, 1970, as a 32-bit
	 * value), or a number of seconds starting from current time. In the
	 * latter case, this number of seconds may not exceed 60*60*24*30 (number
	 * of seconds in 30 days); if the number sent by a client is larger than
	 * that, the server will consider it to be real Unix time value rather
	 * than an offset from current time.
	 * </p>
	 * </blockquote>
	 *
	 * @param key the key under which this object should be added.
	 * @param exp the expiration of this object
	 * @param o the object to store
	 * @return a future representing the processing of this operation
	 * @throws IllegalStateException in the rare circumstance where queue
	 *         is too full to accept any more requests
	 */
	public OperationFuture<Boolean> add(String key, int exp, Object o) {
		return asyncStore(StoreType.add, key, exp, o, transcoder);
	}

	/**
	 * Set an object in the cache regardless of any existing value.
	 *
	 * <p>
	 * The <code>exp</code> value is passed along to memcached exactly as
	 * given, and will be processed per the memcached protocol specification:
	 * </p>
	 *
	 * <p>Note that the return will be false any time a mutation has not
	 * occurred.</p>
	 *
	 * <blockquote>
	 * <p>
	 * The actual value sent may either be
	 * Unix time (number of seconds since January 1, 1970, as a 32-bit
	 * value), or a number of seconds starting from current time. In the
	 * latter case, this number of seconds may not exceed 60*60*24*30 (number
	 * of seconds in 30 days); if the number sent by a client is larger than
	 * that, the server will consider it to be real Unix time value rather
	 * than an offset from current time.
	 * </p>
	 * </blockquote>
	 *
	 * @param <T>
	 * @param key the key under which this object should be added.
	 * @param exp the expiration of this object
	 * @param o the object to store
	 * @param tc the transcoder to serialize and unserialize the value
	 * @return a future representing the processing of this operation
	 * @throws IllegalStateException in the rare circumstance where queue
	 *         is too full to accept any more requests
	 */
	public <T> OperationFuture<Boolean> set(String key, int exp, T o, Transcoder<T> tc) {
		return asyncStore(StoreType.set, key, exp, o, tc);
	}

	/**
	 * Set an object in the cache (using the default transcoder)
	 * regardless of any existing value.
	 *
	 * <p>
	 * The <code>exp</code> value is passed along to memcached exactly as
	 * given, and will be processed per the memcached protocol specification:
	 * </p>
	 *
	 * <p>Note that the return will be false any time a mutation has not
	 * occurred.</p>
	 *
	 * <blockquote>
	 * <p>
	 * The actual value sent may either be
	 * Unix time (number of seconds since January 1, 1970, as a 32-bit
	 * value), or a number of seconds starting from current time. In the
	 * latter case, this number of seconds may not exceed 60*60*24*30 (number
	 * of seconds in 30 days); if the number sent by a client is larger than
	 * that, the server will consider it to be real Unix time value rather
	 * than an offset from current time.
	 * </p>
	 * </blockquote>
	 *
	 * @param key the key under which this object should be added.
	 * @param exp the expiration of this object
	 * @param o the object to store
	 * @return a future representing the processing of this operation
	 * @throws IllegalStateException in the rare circumstance where queue
	 *         is too full to accept any more requests
	 */
	public OperationFuture<Boolean> set(String key, int exp, Object o) {
		return asyncStore(StoreType.set, key, exp, o, transcoder);
	}

	/**
	 * Replace an object with the given value iff there is already a value
	 * for the given key.
	 *
	 * <p>
	 * The <code>exp</code> value is passed along to memcached exactly as
	 * given, and will be processed per the memcached protocol specification:
	 * </p>
	 *
	 * <p>Note that the return will be false any time a mutation has not
	 * occurred.</p>
	 *
	 * <blockquote>
	 * <p>
	 * The actual value sent may either be
	 * Unix time (number of seconds since January 1, 1970, as a 32-bit
	 * value), or a number of seconds starting from current time. In the
	 * latter case, this number of seconds may not exceed 60*60*24*30 (number
	 * of seconds in 30 days); if the number sent by a client is larger than
	 * that, the server will consider it to be real Unix time value rather
	 * than an offset from current time.
	 * </p>
	 * </blockquote>
	 *
	 * @param <T>
	 * @param key the key under which this object should be added.
	 * @param exp the expiration of this object
	 * @param o the object to store
	 * @param tc the transcoder to serialize and unserialize the value
	 * @return a future representing the processing of this operation
	 * @throws IllegalStateException in the rare circumstance where queue
	 *         is too full to accept any more requests
	 */
	public <T> OperationFuture<Boolean> replace(String key, int exp, T o,
		Transcoder<T> tc) {
		return asyncStore(StoreType.replace, key, exp, o, tc);
	}

	/**
	 * Replace an object with the given value (transcoded with the default
	 * transcoder) iff there is already a value for the given key.
	 *
	 * <p>
	 * The <code>exp</code> value is passed along to memcached exactly as
	 * given, and will be processed per the memcached protocol specification:
	 * </p>
	 *
	 * <p>Note that the return will be false any time a mutation has not
	 * occurred.</p>
	 *
	 * <blockquote>
	 * <p>
	 * The actual value sent may either be
	 * Unix time (number of seconds since January 1, 1970, as a 32-bit
	 * value), or a number of seconds starting from current time. In the
	 * latter case, this number of seconds may not exceed 60*60*24*30 (number
	 * of seconds in 30 days); if the number sent by a client is larger than
	 * that, the server will consider it to be real Unix time value rather
	 * than an offset from current time.
	 * </p>
	 * </blockquote>
	 *
	 * @param key the key under which this object should be added.
	 * @param exp the expiration of this object
	 * @param o the object to store
	 * @return a future representing the processing of this operation
	 * @throws IllegalStateException in the rare circumstance where queue
	 *         is too full to accept any more requests
	 */
	public OperationFuture<Boolean> replace(String key, int exp, Object o) {
		return asyncStore(StoreType.replace, key, exp, o, transcoder);
	}

	/**
	 * Get the given key asynchronously.
	 *
	 * @param <T>
	 * @param key the key to fetch
	 * @param tc the transcoder to serialize and unserialize value
	 * @return a future that will hold the return value of the fetch
	 * @throws IllegalStateException in the rare circumstance where queue
	 *         is too full to accept any more requests
	 */
	public <T> GetFuture<T> asyncGet(final String key, final Transcoder<T> tc) {

		final CountDownLatch latch=new CountDownLatch(1);
		final GetFuture<T> rv=new GetFuture<T>(latch, operationTimeout, key);

		Operation op=opFact.get(key,
				new GetOperation.Callback() {
			private Future<T> val=null;
			public void receivedStatus(OperationStatus status) {
				rv.set(val, status);
			}
			public void gotData(String k, int flags, byte[] data) {
				assert key.equals(k) : "Wrong key returned";
				val=tcService.decode(tc,
					new CachedData(flags, data, tc.getMaxSize()));
			}
			public void complete() {
				latch.countDown();
			}});
		rv.setOperation(op);
		addOp(key, op);
		return rv;
	}

	/**
	 * Get the given key asynchronously and decode with the default
	 * transcoder.
	 *
	 * @param key the key to fetch
	 * @return a future that will hold the return value of the fetch
	 * @throws IllegalStateException in the rare circumstance where queue
	 *         is too full to accept any more requests
	 */
	public GetFuture<Object> asyncGet(final String key) {
		return asyncGet(key, transcoder);
	}

	/**
	 * Gets (with CAS support) the given key asynchronously.
	 *
	 * @param <T>
	 * @param key the key to fetch
	 * @param tc the transcoder to serialize and unserialize value
	 * @return a future that will hold the return value of the fetch
	 * @throws IllegalStateException in the rare circumstance where queue
	 *         is too full to accept any more requests
	 */
	public <T> OperationFuture<CASValue<T>> asyncGets(final String key,
			final Transcoder<T> tc) {

		final CountDownLatch latch=new CountDownLatch(1);
		final OperationFuture<CASValue<T>> rv=
			new OperationFuture<CASValue<T>>(key, latch, operationTimeout);

		Operation op=opFact.gets(key,
				new GetsOperation.Callback() {
			private CASValue<T> val=null;
			public void receivedStatus(OperationStatus status) {
				rv.set(val, status);
			}
			public void gotData(String k, int flags, long cas, byte[] data) {
				assert key.equals(k) : "Wrong key returned";
				assert cas > 0 : "CAS was less than zero:  " + cas;
				val=new CASValue<T>(cas, tc.decode(
					new CachedData(flags, data, tc.getMaxSize())));
			}
			public void complete() {
				latch.countDown();
			}});
		rv.setOperation(op);
		addOp(key, op);
		return rv;
	}

	/**
	 * Gets (with CAS support) the given key asynchronously and decode using
	 * the default transcoder.
	 *
	 * @param key the key to fetch
	 * @return a future that will hold the return value of the fetch
	 * @throws IllegalStateException in the rare circumstance where queue
	 *         is too full to accept any more requests
	 */
	public OperationFuture<CASValue<Object>> asyncGets(final String key) {
		return asyncGets(key, transcoder);
	}

	/**
	 * Gets (with CAS support) with a single key.
	 *
	 * @param <T>
	 * @param key the key to get
	 * @param tc the transcoder to serialize and unserialize value
	 * @return the result from the cache and CAS id (null if there is none)
	 * @throws OperationTimeoutException if global operation timeout is
	 * 		   exceeded
	 * @throws IllegalStateException in the rare circumstance where queue
	 *         is too full to accept any more requests
	 */
	public <T> CASValue<T> gets(String key, Transcoder<T> tc) {
		try {
			return asyncGets(key, tc).get(
				operationTimeout, TimeUnit.MILLISECONDS);
		} catch (InterruptedException e) {
			throw new RuntimeException("Interrupted waiting for value", e);
		} catch (ExecutionException e) {
			throw new RuntimeException("Exception waiting for value", e);
		} catch (TimeoutException e) {
			throw new OperationTimeoutException("Timeout waiting for value", e);
		}
	}

	/**
	 * Get with a single key and reset its expiration.
	 *
	 * @param <T>
	 * @param key the key to get
	 * @param exp the new expiration for the key
	 * @param tc the transcoder to serialize and unserialize value
	 * @return the result from the cache (null if there is none)
	 * @throws OperationTimeoutException if the global operation timeout is
	 *		   exceeded
	 * @throws IllegalStateException in the rare circumstance where queue
	 *         is too full to accept any more requests
	 */
	public <T> CASValue<T> getAndTouch(String key, int exp, Transcoder<T> tc) {
		try {
			return asyncGetAndTouch(key, exp, tc).get(operationTimeout,
					TimeUnit.MILLISECONDS);
		} catch (InterruptedException e) {
			throw new RuntimeException("Interrupted waiting for value", e);
		} catch (ExecutionException e) {
			throw new RuntimeException("Exception waiting for value", e);
		} catch (TimeoutException e) {
			throw new OperationTimeoutException("Timeout waiting for value", e);
		}
	}

	/**
	 * Get a single key and reset its expiration using the default transcoder.
	 *
	 * @param key the key to get
	 * @param exp the new expiration for the key
	 * @return the result from the cache and CAS id (null if there is none)
	 * @throws OperationTimeoutException if the global operation timeout is
	 *		   exceeded
	 * @throws IllegalStateException in the rare circumstance where queue
	 *         is too full to accept any more requests
	 */
	public CASValue<Object> getAndTouch(String key, int exp) {
		return getAndTouch(key, exp, transcoder);
	}

	/**
	 * Gets (with CAS support) with a single key using the default transcoder.
	 *
	 * @param key the key to get
	 * @return the result from the cache and CAS id (null if there is none)
	 * @throws OperationTimeoutException if the global operation timeout is
	 *		   exceeded
	 * @throws IllegalStateException in the rare circumstance where queue
	 *         is too full to accept any more requests
	 */
	public CASValue<Object> gets(String key) {
		return gets(key, transcoder);
	}

	/**
	 * Get with a single key.
	 *
	 * @param <T>
	 * @param key the key to get
	 * @param tc the transcoder to serialize and unserialize value
	 * @return the result from the cache (null if there is none)
	 * @throws OperationTimeoutException if the global operation timeout is
	 *		   exceeded
	 * @throws IllegalStateException in the rare circumstance where queue
	 *         is too full to accept any more requests
	 */
	public <T> T get(String key, Transcoder<T> tc) {
		try {
			return asyncGet(key, tc).get(
				operationTimeout, TimeUnit.MILLISECONDS);
		} catch (InterruptedException e) {
			throw new RuntimeException("Interrupted waiting for value", e);
		} catch (ExecutionException e) {
			throw new RuntimeException("Exception waiting for value", e);
		} catch (TimeoutException e) {
			throw new OperationTimeoutException("Timeout waiting for value", e);
		}
	}

	/**
	 * Get with a single key and decode using the default transcoder.
	 *
	 * @param key the key to get
	 * @return the result from the cache (null if there is none)
	 * @throws OperationTimeoutException if the global operation timeout is
	 *		   exceeded
	 * @throws IllegalStateException in the rare circumstance where queue
	 *         is too full to accept any more requests
	 */
	public Object get(String key) {
		return get(key, transcoder);
	}

	/**
	 * Asynchronously get a bunch of objects from the cache.
	 *
	 * @param <T>
	 * @param keys the keys to request
	 * @param tc_iter an iterator of transcoders to serialize and
	 *        unserialize values; the transcoders are matched with
	 *        the keys in the same order.  The minimum of the key
	 *        collection length and number of transcoders is used
	 *        and no exception is thrown if they do not match
	 * @return a Future result of that fetch
	 * @throws IllegalStateException in the rare circumstance where queue
	 *         is too full to accept any more requests
	 */
	public <T> BulkFuture<Map<String, T>> asyncGetBulk(Collection<String> keys,
		Iterator<Transcoder<T>> tc_iter) {
		final Map<String, Future<T>> m=new ConcurrentHashMap<String, Future<T>>();

		final Map<String, Transcoder<T>> tc_map = new HashMap<String, Transcoder<T>>();

		final Map<MemcachedNode, Collection<String>> chunks
			=new HashMap<MemcachedNode, Collection<String>>();
		final NodeLocator locator=mconn.getLocator();
		Iterator<String> key_iter=keys.iterator();
		while (key_iter.hasNext() && tc_iter.hasNext()) {
			String key=key_iter.next();
			tc_map.put(key, tc_iter.next());
			validateKey(key);
			final MemcachedNode primaryNode=locator.getPrimary(key);
			MemcachedNode node=null;
			if(primaryNode.isActive()) {
				node=primaryNode;
			} else {
				for(Iterator<MemcachedNode> i=locator.getSequence(key);
					node == null && i.hasNext();) {
					MemcachedNode n=i.next();
					if(n.isActive()) {
						node=n;
					}
				}
				if(node == null) {
					node=primaryNode;
				}
			}
			assert node != null : "Didn't find a node for " + key;
			Collection<String> ks=chunks.get(node);
			if(ks == null) {
				ks=new ArrayList<String>();
				chunks.put(node, ks);
			}
			ks.add(key);
		}

		final CountDownLatch latch=new CountDownLatch(chunks.size());
		final Collection<Operation> ops=new ArrayList<Operation>(chunks.size());
		final BulkGetFuture<T> rv = new BulkGetFuture<T>(m, ops, latch);

		GetOperation.Callback cb=new GetOperation.Callback() {
				@SuppressWarnings("synthetic-access")
				public void receivedStatus(OperationStatus status) {
					rv.setStatus(status);
					if(!status.isSuccess()) {
						getLogger().warn("Unsuccessful get:  %s", status);
					}
				}
				public void gotData(String k, int flags, byte[] data) {
					Transcoder<T> tc = tc_map.get(k);
					m.put(k, tcService.decode(tc,
							new CachedData(flags, data, tc.getMaxSize())));
				}
				public void complete() {
					latch.countDown();
				}
		};

		final Map<MemcachedNode, Operation> mops=
			new HashMap<MemcachedNode, Operation>();

		for(Map.Entry<MemcachedNode, Collection<String>> me
				: chunks.entrySet()) {
			Operation op=opFact.get(me.getValue(), cb);
			mops.put(me.getKey(), op);
			ops.add(op);
		}
		assert mops.size() == chunks.size();
		mconn.checkState();
		mconn.addOperations(mops);
		return rv;
	}

	/**
	 * Asynchronously get a bunch of objects from the cache.
	 *
	 * @param <T>
	 * @param keys the keys to request
	 * @param tc the transcoder to serialize and unserialize values
	 * @return a Future result of that fetch
	 * @throws IllegalStateException in the rare circumstance where queue
	 *         is too full to accept any more requests
	 */
	public <T> BulkFuture<Map<String, T>> asyncGetBulk(Collection<String> keys,
		Transcoder<T> tc) {
		return asyncGetBulk(keys, new SingleElementInfiniteIterator<Transcoder<T>>(tc));
	}

	/**
	 * Asynchronously get a bunch of objects from the cache and decode them
	 * with the given transcoder.
	 *
	 * @param keys the keys to request
	 * @return a Future result of that fetch
	 * @throws IllegalStateException in the rare circumstance where queue
	 *         is too full to accept any more requests
	 */
	public BulkFuture<Map<String, Object>> asyncGetBulk(Collection<String> keys) {
		return asyncGetBulk(keys, transcoder);
	}

	/**
	 * Varargs wrapper for asynchronous bulk gets.
	 *
	 * @param <T>
	 * @param tc the transcoder to serialize and unserialize value
	 * @param keys one more more keys to get
	 * @return the future values of those keys
	 * @throws IllegalStateException in the rare circumstance where queue
	 *         is too full to accept any more requests
	 */
	public <T> BulkFuture<Map<String, T>> asyncGetBulk(Transcoder<T> tc,
		String... keys) {
		return asyncGetBulk(Arrays.asList(keys), tc);
	}

	/**
	 * Varargs wrapper for asynchronous bulk gets with the default transcoder.
	 *
	 * @param keys one more more keys to get
	 * @return the future values of those keys
	 * @throws IllegalStateException in the rare circumstance where queue
	 *         is too full to accept any more requests
	 */
	public BulkFuture<Map<String, Object>> asyncGetBulk(String... keys) {
		return asyncGetBulk(Arrays.asList(keys), transcoder);
	}

	/**
	 * Get the given key to reset its expiration time.
	 *
	 * @param key the key to fetch
	 * @param exp the new expiration to set for the given key
	 * @return a future that will hold the return value of the fetch
	 * @throws IllegalStateException in the rare circumstance where queue
	 *         is too full to accept any more requests
	 */
	public OperationFuture<CASValue<Object>> asyncGetAndTouch(final String key, final int exp) {
		return asyncGetAndTouch(key, exp, transcoder);
	}

	/**
	 * Get the given key to reset its expiration time.
	 *
	 * @param key the key to fetch
	 * @param exp the new expiration to set for the given key
	 * @param tc the transcoder to serialize and unserialize value
	 * @return a future that will hold the return value of the fetch
	 * @throws IllegalStateException in the rare circumstance where queue
	 *         is too full to accept any more requests
	 */
	public <T> OperationFuture<CASValue<T>> asyncGetAndTouch(final String key, final int exp,
			final Transcoder<T> tc) {
		final CountDownLatch latch=new CountDownLatch(1);
		final OperationFuture<CASValue<T>> rv=new OperationFuture<CASValue<T>>(key, latch,
				operationTimeout);

		Operation op=opFact.getAndTouch(key, exp, new GetAndTouchOperation.Callback() {
			private CASValue<T> val=null;
			public void receivedStatus(OperationStatus status) {
				rv.set(val, status);
			}
			public void complete() {
				latch.countDown();
			}
			public void gotData(String k, int flags, long cas, byte[] data) {
				assert k.equals(key) : "Wrong key returned";
				assert cas > 0 : "CAS was less than zero:  " + cas;
				val=new CASValue<T>(cas, tc.decode(
					new CachedData(flags, data, tc.getMaxSize())));
			}});
		rv.setOperation(op);
		addOp(key, op);
		return rv;
	}

	/**
	 * Get the values for multiple keys from the cache.
	 *
	 * @param <T>
	 * @param keys the keys
	 * @param tc the transcoder to serialize and unserialize value
	 * @return a map of the values (for each value that exists)
	 * @throws OperationTimeoutException if the global operation timeout is
	 *		   exceeded
	 * @throws IllegalStateException in the rare circumstance where queue
	 *         is too full to accept any more requests
	 */
	public <T> Map<String, T> getBulk(Collection<String> keys,
			Transcoder<T> tc) {
		try {
			return asyncGetBulk(keys, tc).get(
				operationTimeout, TimeUnit.MILLISECONDS);
		} catch (InterruptedException e) {
			throw new RuntimeException("Interrupted getting bulk values", e);
		} catch (ExecutionException e) {
			throw new RuntimeException("Failed getting bulk values", e);
		} catch (TimeoutException e) {
			throw new OperationTimeoutException(
				"Timeout waiting for bulkvalues", e);
		}
	}

	/**
	 * Get the values for multiple keys from the cache.
	 *
	 * @param keys the keys
	 * @return a map of the values (for each value that exists)
	 * @throws OperationTimeoutException if the global operation timeout is
	 *		   exceeded
	 * @throws IllegalStateException in the rare circumstance where queue
	 *         is too full to accept any more requests
	 */
	public Map<String, Object> getBulk(Collection<String> keys) {
		return getBulk(keys, transcoder);
	}

	/**
	 * Get the values for multiple keys from the cache.
	 *
	 * @param <T>
	 * @param tc the transcoder to serialize and unserialize value
	 * @param keys the keys
	 * @return a map of the values (for each value that exists)
	 * @throws OperationTimeoutException if the global operation timeout is
	 *		   exceeded
	 * @throws IllegalStateException in the rare circumstance where queue
	 *         is too full to accept any more requests
	 */
	public <T> Map<String, T> getBulk(Transcoder<T> tc, String... keys) {
		return getBulk(Arrays.asList(keys), tc);
	}

	/**
	 * Get the values for multiple keys from the cache.
	 *
	 * @param keys the keys
	 * @return a map of the values (for each value that exists)
	 * @throws OperationTimeoutException if the global operation timeout is
	 *		   exceeded
	 * @throws IllegalStateException in the rare circumstance where queue
	 *         is too full to accept any more requests
	 */
	public Map<String, Object> getBulk(String... keys) {
		return getBulk(Arrays.asList(keys), transcoder);
	}

	/**
	 * Get the versions of all of the connected memcacheds.
	 *
	 * @return a Map of SocketAddress to String for connected servers
	 * @throws IllegalStateException in the rare circumstance where queue
	 *         is too full to accept any more requests
	 */
	public Map<SocketAddress, String> getVersions() {
		final Map<SocketAddress, String>rv=
			new ConcurrentHashMap<SocketAddress, String>();

		CountDownLatch blatch = broadcastOp(new BroadcastOpFactory(){
			public Operation newOp(final MemcachedNode n,
					final CountDownLatch latch) {
				final SocketAddress sa=n.getSocketAddress();
				return opFact.version(
						new OperationCallback() {
							public void receivedStatus(OperationStatus s) {
								rv.put(sa, s.getMessage());
							}
							public void complete() {
								latch.countDown();
							}
						});
			}});
		try {
			blatch.await(operationTimeout, TimeUnit.MILLISECONDS);
		} catch (InterruptedException e) {
			throw new RuntimeException("Interrupted waiting for versions", e);
		}
		return rv;
	}

	/**
	 * Get all of the stats from all of the connections.
	 *
	 * @return a Map of a Map of stats replies by SocketAddress
	 * @throws IllegalStateException in the rare circumstance where queue
	 *         is too full to accept any more requests
	 */
	public Map<SocketAddress, Map<String, String>> getStats() {
		return getStats(null);
	}

	/**
	 * Get a set of stats from all connections.
	 *
	 * @param arg which stats to get
	 * @return a Map of the server SocketAddress to a map of String stat
	 *		   keys to String stat values.
	 * @throws IllegalStateException in the rare circumstance where queue
	 *         is too full to accept any more requests
	 */
	public Map<SocketAddress, Map<String, String>> getStats(final String arg) {
		final Map<SocketAddress, Map<String, String>> rv
			=new HashMap<SocketAddress, Map<String, String>>();

		CountDownLatch blatch = broadcastOp(new BroadcastOpFactory(){
			public Operation newOp(final MemcachedNode n,
				final CountDownLatch latch) {
				final SocketAddress sa=n.getSocketAddress();
				rv.put(sa, new HashMap<String, String>());
				return opFact.stats(arg,
						new StatsOperation.Callback() {
					public void gotStat(String name, String val) {
						rv.get(sa).put(name, val);
					}
					public void receivedStatus(OperationStatus status) {
						if(!status.isSuccess()) {
							getLogger().warn("Unsuccessful stat fetch:	%s",
									status);
						}
					}
					public void complete() {
						latch.countDown();
					}});
			}});
		try {
			blatch.await(operationTimeout, TimeUnit.MILLISECONDS);
		} catch (InterruptedException e) {
			throw new RuntimeException("Interrupted waiting for stats", e);
		}
		return rv;
	}

	private long mutate(Mutator m, String key, int by, long def, int exp) {
		final AtomicLong rv=new AtomicLong();
		final CountDownLatch latch=new CountDownLatch(1);
		addOp(key, opFact.mutate(m, key, by, def, exp, new OperationCallback() {
					public void receivedStatus(OperationStatus s) {
						rv.set(new Long(s.isSuccess()?s.getMessage():"-1"));
					}
					public void complete() {
						latch.countDown();
					}}));
		try {
			if (!latch.await(operationTimeout, TimeUnit.MILLISECONDS)) {
				throw new OperationTimeoutException(
					"Mutate operation timed out, unable to modify counter ["
						+ key + "]");
			}
		} catch (InterruptedException e) {
			throw new RuntimeException("Interrupted", e);
		}
		getLogger().debug("Mutation returned %s", rv);
		return rv.get();
	}

	/**
	 * Increment the given key by the given amount.
	 *
	 * Due to the way the memcached server operates on items, incremented
	 * and decremented items will be returned as Strings with any
	 * operations that return a value.
	 *
	 * @param key the key
	 * @param by the amount to increment
	 * @return the new value (-1 if the key doesn't exist)
	 * @throws OperationTimeoutException if the global operation timeout is
	 *		   exceeded
	 * @throws IllegalStateException in the rare circumstance where queue
	 *         is too full to accept any more requests
	 */
	public long incr(String key, int by) {
		return mutate(Mutator.incr, key, by, 0, -1);
	}

	/**
	 * Decrement the given key by the given value.
	 *
	 * Due to the way the memcached server operates on items, incremented
	 * and decremented items will be returned as Strings with any
	 * operations that return a value.
	 *
	 * @param key the key
	 * @param by the value
	 * @return the new value (-1 if the key doesn't exist)
	 * @throws OperationTimeoutException if the global operation timeout is
	 *		   exceeded
	 * @throws IllegalStateException in the rare circumstance where queue
	 *         is too full to accept any more requests
	 */
	public long decr(String key, int by) {
		return mutate(Mutator.decr, key, by, 0, -1);
	}

	/**
	 * Increment the given counter, returning the new value.
	 *
	 * Due to the way the memcached server operates on items, incremented
	 * and decremented items will be returned as Strings with any
	 * operations that return a value.
	 *
	 * @param key the key
	 * @param by the amount to increment
	 * @param def the default value (if the counter does not exist)
	 * @param exp the expiration of this object
	 * @return the new value, or -1 if we were unable to increment or add
	 * @throws OperationTimeoutException if the global operation timeout is
	 *		   exceeded
	 * @throws IllegalStateException in the rare circumstance where queue
	 *         is too full to accept any more requests
	 */
	public long incr(String key, int by, long def, int exp) {
		return mutateWithDefault(Mutator.incr, key, by, def, exp);
	}

	/**
	 * Decrement the given counter, returning the new value.
	 *
	 * Due to the way the memcached server operates on items, incremented
	 * and decremented items will be returned as Strings with any
	 * operations that return a value.
	 *
	 * @param key the key
	 * @param by the amount to decrement
	 * @param def the default value (if the counter does not exist)
	 * @param exp the expiration of this object
	 * @return the new value, or -1 if we were unable to decrement or add
	 * @throws OperationTimeoutException if the global operation timeout is
	 *		   exceeded
	 * @throws IllegalStateException in the rare circumstance where queue
	 *         is too full to accept any more requests
	 */
	public long decr(String key, int by, long def, int exp) {
		return mutateWithDefault(Mutator.decr, key, by, def, exp);
	}


	private long mutateWithDefault(Mutator t, String key,
			int by, long def, int exp) {
		long rv=mutate(t, key, by, def, exp);
		if(rv == -1) {
			Future<Boolean> f=asyncStore(StoreType.add,
					key, exp, String.valueOf(def));
			try {
				if(f.get(operationTimeout, TimeUnit.MILLISECONDS)) {
					rv=def;
				} else {
					rv=mutate(t, key, by, 0, exp);
					assert rv != -1 : "Failed to mutate or init value";
				}
			} catch (InterruptedException e) {
				throw new RuntimeException("Interrupted waiting for store", e);
			} catch (ExecutionException e) {
				throw new RuntimeException("Failed waiting for store", e);
			} catch (TimeoutException e) {
				throw new OperationTimeoutException(
					"Timeout waiting to mutate or init value", e);
			}
		}
		return rv;
	}

	private OperationFuture<Long> asyncMutate(Mutator m, String key, int by, long def,
			int exp) {
		final CountDownLatch latch = new CountDownLatch(1);
		final OperationFuture<Long> rv = new OperationFuture<Long>(key,
				latch, operationTimeout);
		Operation op = addOp(key, opFact.mutate(m, key, by, def, exp,
				new OperationCallback() {
			public void receivedStatus(OperationStatus s) {
				rv.set(new Long(s.isSuccess() ? s.getMessage() : "-1"), s);
			}
			public void complete() {
				latch.countDown();
			}
		}));
		rv.setOperation(op);
		return rv;
	}

	/**
	 * Asychronous increment.
	 *
	 * @param key key to increment
	 * @param by the amount to increment the value by
	 * @return a future with the incremented value, or -1 if the
	 *		   increment failed.
	 * @throws IllegalStateException in the rare circumstance where queue
	 *         is too full to accept any more requests
	 */
	public OperationFuture<Long> asyncIncr(String key, int by) {
		return asyncMutate(Mutator.incr, key, by, 0, -1);
	}

	/**
	 * Asynchronous decrement.
	 *
	 * @param key key to increment
	 * @param by the amount to increment the value by
	 * @return a future with the decremented value, or -1 if the
	 *		   increment failed.
	 * @throws IllegalStateException in the rare circumstance where queue
	 *         is too full to accept any more requests
	 */
	public OperationFuture<Long> asyncDecr(String key, int by) {
		return asyncMutate(Mutator.decr, key, by, 0, -1);
	}

	/**
	 * Increment the given counter, returning the new value.
	 *
	 * @param key the key
	 * @param by the amount to increment
	 * @param def the default value (if the counter does not exist)
	 * @return the new value, or -1 if we were unable to increment or add
	 * @throws OperationTimeoutException if the global operation timeout is
	 *		   exceeded
	 * @throws IllegalStateException in the rare circumstance where queue
	 *         is too full to accept any more requests
	 */
	public long incr(String key, int by, long def) {
		return mutateWithDefault(Mutator.incr, key, by, def, 0);
	}

	/**
	 * Decrement the given counter, returning the new value.
	 *
	 * @param key the key
	 * @param by the amount to decrement
	 * @param def the default value (if the counter does not exist)
	 * @return the new value, or -1 if we were unable to decrement or add
	 * @throws OperationTimeoutException if the global operation timeout is
	 *		   exceeded
	 * @throws IllegalStateException in the rare circumstance where queue
	 *         is too full to accept any more requests
	 */
	public long decr(String key, int by, long def) {
		return mutateWithDefault(Mutator.decr, key, by, def, 0);
	}

	/**
	 * Delete the given key from the cache.
	 *
	 * <p>
	 * The hold argument specifies the amount of time in seconds (or Unix time
	 * until which) the client wishes the server to refuse "add" and "replace"
	 * commands with this key. For this amount of item, the item is put into a
	 * delete queue, which means that it won't possible to retrieve it by the
	 * "get" command, but "add" and "replace" command with this key will also
	 * fail (the "set" command will succeed, however). After the time passes,
	 * the item is finally deleted from server memory.
	 * </p>
	 *
	 * @param key the key to delete
	 * @param hold how long the key should be unavailable to add commands
	 *
	 * @return whether or not the operation was performed
	 * @deprecated Hold values are no longer honored.
	 */
	@Deprecated
	public OperationFuture<Boolean> delete(String key, int hold) {
		return delete(key);
	}

	/**
	 * Delete the given key from the cache.
	 *
	 * @param key the key to delete
	 * @return whether or not the operation was performed
	 * @throws IllegalStateException in the rare circumstance where queue
	 *         is too full to accept any more requests
	 */
	public OperationFuture<Boolean> delete(String key) {
		final CountDownLatch latch=new CountDownLatch(1);
		final OperationFuture<Boolean> rv=new OperationFuture<Boolean>(key, latch,
			operationTimeout);
		DeleteOperation op=opFact.delete(key,
				new OperationCallback() {
					public void receivedStatus(OperationStatus s) {
						rv.set(s.isSuccess(), s);
					}
					public void complete() {
						latch.countDown();
					}});
		rv.setOperation(op);
		addOp(key, op);
		return rv;
	}

	/**
	 * Flush all caches from all servers with a delay of application.
	 * @param delay the period of time to delay, in seconds
	 * @return whether or not the operation was accepted
	 * @throws IllegalStateException in the rare circumstance where queue
	 *         is too full to accept any more requests
	 */
	public OperationFuture<Boolean> flush(final int delay) {
		final AtomicReference<Boolean> flushResult=
			new AtomicReference<Boolean>(null);
		final ConcurrentLinkedQueue<Operation> ops=
			new ConcurrentLinkedQueue<Operation>();
		CountDownLatch blatch = broadcastOp(new BroadcastOpFactory(){
			public Operation newOp(final MemcachedNode n,
					final CountDownLatch latch) {
				Operation op=opFact.flush(delay, new OperationCallback(){
					public void receivedStatus(OperationStatus s) {
						flushResult.set(s.isSuccess());
					}
					public void complete() {
						latch.countDown();
					}});
				ops.add(op);
				return op;
			}});

		return new OperationFuture<Boolean>(null, blatch, flushResult,
				operationTimeout) {
			@Override
			public boolean cancel(boolean ign) {
				boolean rv=false;
				for(Operation op : ops) {
					op.cancel();
					rv |= op.getState() == OperationState.WRITING;
				}
				return rv;
			}
			@Override
			public Boolean get(long duration, TimeUnit units) throws InterruptedException,
				TimeoutException, ExecutionException {
				status = new OperationStatus(true, "OK");
				return super.get(duration, units);
			}
			@Override
			public boolean isCancelled() {
				boolean rv=false;
				for(Operation op : ops) {
					rv |= op.isCancelled();
				}
				return rv;
			}
			@Override
			public boolean isDone() {
				boolean rv=true;
				for(Operation op : ops) {
					rv &= op.getState() == OperationState.COMPLETE;
				}
				return rv || isCancelled();
			}
		};
	}

	/**
	 * Flush all caches from all servers immediately.
	 * @return whether or not the operation was performed
	 * @throws IllegalStateException in the rare circumstance where queue
	 *         is too full to accept any more requests
	 */
	public OperationFuture<Boolean> flush() {
		return flush(-1);
	}

	public Set<String> listSaslMechanisms() {
		final ConcurrentMap<String, String> rv
			= new ConcurrentHashMap<String, String>();

		CountDownLatch blatch = broadcastOp(new BroadcastOpFactory() {
			public Operation newOp(MemcachedNode n,
					final CountDownLatch latch) {
				return opFact.saslMechs(new OperationCallback() {
					public void receivedStatus(OperationStatus status) {
						for(String s : status.getMessage().split(" ")) {
							rv.put(s, s);
						}
					}
					public void complete() {
						latch.countDown();
					}
				});
			}
		});

		try {
			blatch.await();
		} catch(InterruptedException e) {
			Thread.currentThread().interrupt();
		}

		return rv.keySet();
	}

	/**
	 * Shut down immediately.
	 */
	public void shutdown() {
		shutdown(-1, TimeUnit.MILLISECONDS);
	}

	/**
	 * Shut down this client gracefully.
	 *
	 * @param timeout the amount of time time for shutdown
	 * @param unit the TimeUnit for the timeout
	 * @return result of the shutdown request
	 */
	public boolean shutdown(long timeout, TimeUnit unit) {
		if(shuttingDown) {
			getLogger().info("Suppressing duplicate attempt to shut down");
			return false;
		}
		shuttingDown=true;
		String baseName=mconn.getName();
		mconn.setName(baseName + " - SHUTTING DOWN");
		boolean rv=false;
		try {
			if(timeout > 0) {
				mconn.setName(baseName + " - SHUTTING DOWN (waiting)");
				rv=waitForQueues(timeout, unit);
			}
		} finally {
			try {
				mconn.setName(baseName + " - SHUTTING DOWN (telling client)");
				mconn.shutdown();
				mconn.setName(baseName + " - SHUTTING DOWN (informed client)");
				tcService.shutdown();
			} catch (IOException e) {
				getLogger().warn("exception while shutting down", e);
			}
		}
		return rv;
	}

	/**
	 * Wait for the queues to die down.
	 *
	 * @param timeout the amount of time time for shutdown
	 * @param unit the TimeUnit for the timeout
	 * @return result of the request for the wait
	 * @throws IllegalStateException in the rare circumstance where queue
	 *         is too full to accept any more requests
	 */
	public boolean waitForQueues(long timeout, TimeUnit unit) {
		CountDownLatch blatch = broadcastOp(new BroadcastOpFactory(){
			public Operation newOp(final MemcachedNode n,
					final CountDownLatch latch) {
				return opFact.noop(
						new OperationCallback() {
							public void complete() {
								latch.countDown();
							}
							public void receivedStatus(OperationStatus s) {
							}
						});
			}}, mconn.getLocator().getAll(), false);
		try {
			return blatch.await(timeout, unit);
		} catch (InterruptedException e) {
			throw new RuntimeException("Interrupted waiting for queues", e);
		}
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
		boolean rv = mconn.addObserver(obs);
		if(rv) {
			for(MemcachedNode node : mconn.getLocator().getAll()) {
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
		return mconn.removeObserver(obs);
	}

	public void connectionEstablished(SocketAddress sa, int reconnectCount) {
		if(authDescriptor != null) {
                    if (authDescriptor.authThresholdReached()) {
                        this.shutdown();
                    }
			authMonitor.authConnection(mconn, opFact, authDescriptor, findNode(sa));
		}
	}

	private MemcachedNode findNode(SocketAddress sa) {
		MemcachedNode node = null;
		for(MemcachedNode n : mconn.getLocator().getAll()) {
			if(n.getSocketAddress().equals(sa)) {
				node = n;
			}
		}
		assert node != null : "Couldn't find node connected to " + sa;
		return node;
	}

	public void connectionLost(SocketAddress sa) {
	}
