	private IValueChangeListener<Boolean> listener = new IValueChangeListener<Boolean>() {
		@Override
		public void handleValueChange(ValueChangeEvent<? extends Boolean> event) {
			if (!updating) {
				IObservableValue<? extends Boolean> observable = event
						.getObservableValue();
				if (Boolean.TRUE.equals(observable.getValue())) {
					notifyIfChanged(indexOfObservable(observable));
				}
