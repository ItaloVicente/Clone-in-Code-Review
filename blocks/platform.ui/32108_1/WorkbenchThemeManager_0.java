	private void init() {
		synchronized (this) {
			if (initialized) {
				return;
			}
			initialized = true;
		}
