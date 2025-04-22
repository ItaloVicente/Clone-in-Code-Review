	private IValueChangeListener validationStatusChangeListener = event -> {
		IStatus oldStatus = (IStatus) event.diff.getOldValue();
		IStatus newStatus = (IStatus) event.diff.getNewValue();
		if (stale && !isValid(oldStatus) && isValid(newStatus)) {
			stale = false;
			updateWrappedSet(new HashSet(target));
