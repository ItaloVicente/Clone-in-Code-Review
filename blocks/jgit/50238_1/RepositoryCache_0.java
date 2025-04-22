	public static void unregister(Key location) {
		cache.unregisterRepository(location);
	}

	public static Collection<Key> getRegistered() {
		return cache.getRegisteredKeys();
	}

