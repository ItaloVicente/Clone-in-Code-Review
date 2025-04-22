		Display.getDefault().asyncExec(() -> WWinPluginAction.refreshActionList());
	}

	protected void saveDialogSettings() {
		if (dialogSettings == null) {
			return;
		}

		try {
			IPath path = getStateLocationOrNull();
			if (path == null) {
