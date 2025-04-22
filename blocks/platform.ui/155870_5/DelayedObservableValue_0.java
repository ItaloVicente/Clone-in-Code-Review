	private T internalGetValue() {
		ObservableTracker.setIgnore(true);
		try {
			return observable.getValue();
		} finally {
			ObservableTracker.setIgnore(false);
		}
	}

