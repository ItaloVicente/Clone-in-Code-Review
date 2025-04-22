	private IValueChangeListener statusChangeListener = new IValueChangeListener() {
		@Override
		public void handleValueChange(ValueChangeEvent event) {
			statusChanged((IStatus) validationStatus.getValue());
		}
	};
