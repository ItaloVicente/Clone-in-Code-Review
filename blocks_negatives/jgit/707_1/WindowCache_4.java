	static final ByteWindow get(final PackFile pack, final long offset)
			throws IOException {
		final WindowCache c = cache;
		final ByteWindow r = c.getOrLoad(pack, c.toStart(offset));
		if (c != cache) {
			c.removeAll();
		}
		return r;
	}

	static final void purge(final PackFile pack) {
		cache.removeAll(pack);
	}

