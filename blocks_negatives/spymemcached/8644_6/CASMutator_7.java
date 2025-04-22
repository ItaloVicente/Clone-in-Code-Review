	/**
	 * Construct a CASMutator that uses the given client.
	 *
	 * @param c the client
	 * @param tc the Transcoder to use
	 * @param max_tries the maximum number of attempts to get a CAS to succeed
	 */
	public CASMutator(MemcachedClientIF c, Transcoder<T> tc, int max_tries) {
		super();
		client=c;
		transcoder=tc;
		max=max_tries;
	}
