	private void attachSelectionTracker() {
		if (selectionTracker == null) {
			selectionTracker = new GitHistorySelectionTracker();
			selectionTracker.attach(getSite().getPage());
		}
	}

	private void detachSelectionTracker() {
		if (selectionTracker != null) {
			selectionTracker.detach(getSite().getPage());
		}
	}

