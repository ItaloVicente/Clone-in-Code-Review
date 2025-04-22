	ByteWindow getWindow(final long offset) throws IOException {
		WindowCache c = windowCache.get();
		ByteWindow window = c.get(this
		if (c != windowCache.get()) {
			c.removeAll();
		}
		return window;
	}

