	private IValueChangeListener validationStatusChangeListener = new IValueChangeListener() {
		@Override
		public void handleValueChange(ValueChangeEvent event) {
			IStatus oldStatus = (IStatus) event.diff.getOldValue();
			IStatus newStatus = (IStatus) event.diff.getNewValue();
			if (stale && !isValid(oldStatus) && isValid(newStatus)) {
				stale = false;
				updateWrappedMap(new HashMap(target));
