	static synchronized void purge(final PackFile file) {
		for (final Slot e : cache) {
			if (e.provider == file) {
				clearEntry(e);
				unlink(e);
			}
		}
	}

	private static void moveToHead(final Slot e) {
