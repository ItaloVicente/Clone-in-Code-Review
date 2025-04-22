	private T getValueInternal() {
		ObservableTracker.getterCalled(this);
		ObservableTracker.setIgnore(true);
		try {
			return observable.getValue();
		} finally {
			ObservableTracker.setIgnore(false);
		}
	}

