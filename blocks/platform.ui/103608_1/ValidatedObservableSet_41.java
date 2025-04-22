	private ISetChangeListener targetChangeListener = event -> {
		if (updatingTarget)
			return;
		IStatus status = (IStatus) validationStatus.getValue();
		if (isValid(status)) {
			if (stale) {
				stale = false;
				updateWrappedSet(new HashSet(target));
