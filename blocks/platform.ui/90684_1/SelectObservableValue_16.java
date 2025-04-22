	private IValueChangeListener<Boolean> listener = event -> {
		if (!updating) {
			IObservableValue<? extends Boolean> observable = event
					.getObservableValue();
			if (Boolean.TRUE.equals(observable.getValue())) {
				notifyIfChanged(indexOfObservable(observable));
