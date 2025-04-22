	private void removeRepositoryListeners() {
		if (refsChangedListener != null) {
			refsChangedListener.remove();
			refsChangedListener = null;
		}
		if (configChangedListener != null) {
			configChangedListener.remove();
			configChangedListener = null;
		}
	}

