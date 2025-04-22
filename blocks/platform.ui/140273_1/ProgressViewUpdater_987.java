		return singleton;
	}

	static boolean hasSingleton() {
		return singleton != null;
	}

	static void clearSingleton() {
		if (singleton != null) {
