		setDefault(me);
	}

	/**
	 * Add a cached authentication for future use.
	 * 
	 * @param ca
	 *            the information we should remember.
	 */
	public static void add(final CachedAuthentication ca) {
		synchronized (me) {
			me.cached.add(ca);
		}
