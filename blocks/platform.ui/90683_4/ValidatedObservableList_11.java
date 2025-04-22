	private IListChangeListener targetChangeListener = event -> {
		if (updatingTarget)
			return;
		IStatus status = (IStatus) validationStatus.getValue();
		if (isValid(status)) {
			if (stale) {
				stale = false;
				updateWrappedList(new ArrayList(target));
