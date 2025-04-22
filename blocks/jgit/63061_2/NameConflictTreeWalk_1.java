	void stopWalk() throws CorruptObjectException {
		while (depth > 0) {
			exitSubtree();
			popEntriesEqual();
		}
		super.stopWalk();
	}

