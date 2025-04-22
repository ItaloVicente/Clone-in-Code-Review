	private IValueChangeListener listener = new IValueChangeListener() {
		@Override
		public void handleValueChange(ValueChangeEvent event) {
			if (!updating) {
				fireValueChange(Diffs.createValueDiff(currentValue,
						doGetValue()));
			}
