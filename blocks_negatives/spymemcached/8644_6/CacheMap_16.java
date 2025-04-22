	/**
	 * Construct a CacheMap over the given MemcachedClient.
	 *
	 * @param c the client
	 * @param expiration the expiration to set for keys written to the cache
	 * @param prefix a prefix used to make keys in this map unique
	 */
	public CacheMap(MemcachedClientIF c, int expiration, String prefix) {
		super(c, expiration, prefix, c.getTranscoder());
	}

	/**
	 * Construct a CacheMap over the given MemcachedClient with no expiration.
	 *
	 * <p>
	 *  Keys written into this Map will only expire when the LRU pushes them
	 *  out.
	 * </p>
	 *
	 * @param c the client
	 * @param prefix a prefix used to make keys in this map unique
	 */
	public CacheMap(MemcachedClientIF c, String prefix) {
		this(c, 0, prefix);
	}
