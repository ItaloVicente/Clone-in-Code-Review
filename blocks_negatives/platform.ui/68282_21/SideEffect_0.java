		checkState();
		if (resumed) {
			return;
		}
		resumed = true;
		if (dirty) {
