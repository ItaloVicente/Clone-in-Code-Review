	/**
	 * Construct a CASMutator that uses the given client.
	 *
	 * @param c the client
	 * @param tc the Transcoder to use
	 */
	public CASMutator(MemcachedClientIF c, Transcoder<T> tc) {
		this(c, tc, MAX_TRIES);
	}
