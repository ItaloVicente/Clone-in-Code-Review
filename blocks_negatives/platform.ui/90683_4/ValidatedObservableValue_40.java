	private IValueChangeListener targetChangeListener = new IValueChangeListener() {
		@Override
		public void handleValueChange(ValueChangeEvent event) {
			if (updatingTarget)
				return;
			IStatus status = (IStatus) validationStatus.getValue();
			if (isValid(status))
				internalSetValue(event.diff.getNewValue(), false);
			else
				makeStale();
		}
