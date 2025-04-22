	private IValueChangeListener targetChangeListener = event -> {
		if (updatingTarget)
			return;
		IStatus status = (IStatus) validationStatus.getValue();
		if (isValid(status))
			internalSetValue(event.diff.getNewValue(), false);
		else
			makeStale();
