		activeEntry.location.update();
		printEntries("updateEntry"); //$NON-NLS-1$
	}

	void forward() {
		if (isPerTabHistoryEnabled()) {
			forwardForTab();
			return;
		}
		if (canForward()) {
