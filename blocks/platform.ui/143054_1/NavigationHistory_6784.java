	}

	void backward() {
		if (isPerTabHistoryEnabled()) {
			backwardForTab();
			return;
		}
		if (canBackward()) {
