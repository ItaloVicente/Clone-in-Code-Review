	public static void unregister(Key location) {
		cache.unregisterRepository(location);
	}

	public static Collection<Key> getRegisteredKeys() {
		return cache.getKeys();
	}

