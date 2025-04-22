	private void busyGC() {
		for (int i = 0; i < 2; ++i) {
			System.gc();
			System.runFinalization();
		}
	}
