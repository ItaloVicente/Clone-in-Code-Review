
	private void deferUpdates(boolean shouldDefer) {
		if (shouldDefer) {
			if (deferCount == 0) {
				startDeferring();
			}
			deferCount++;
		} else {
			deferCount--;
			if (deferCount == 0) {
				handleDeferredEvents();
			}
		}
	}

	private void startDeferring() {
