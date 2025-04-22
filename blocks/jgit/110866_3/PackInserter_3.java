	private Inflater inflater() {
		if (cachedInflater == null) {
			cachedInflater = InflaterCache.get();
		} else {
			cachedInflater.reset();
		}
		return cachedInflater;
	}

