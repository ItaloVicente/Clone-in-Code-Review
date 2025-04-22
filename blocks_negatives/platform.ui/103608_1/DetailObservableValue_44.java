	private IValueChangeListener<T> innerChangeListener = new IValueChangeListener<T>() {
		@Override
		public void handleValueChange(ValueChangeEvent<? extends T> event) {
			if (!updating) {
				fireValueChange(Diffs.unmodifiableDiff(event.diff));
			}
