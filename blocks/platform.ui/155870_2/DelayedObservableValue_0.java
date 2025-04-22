	private T getValueInternal() {
		ObservableTracker.setIgnore(true);
		try {
			return observable.getValue();
		} finally {
			ObservableTracker.setIgnore(false);
		}
	}

