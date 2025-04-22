	}

	private void unhookListeners() {
		PlatformUI.getWorkbench().getActivitySupport().getActivityManager().removeActivityManagerListener(this);

		IIdentifier id = getIdentifier();
		if (id != null) {
