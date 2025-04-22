	}

	protected void refreshPluginActions() {
		if (!PlatformUI.isWorkbenchRunning()) {
			return;
		}

		Display.getDefault().asyncExec(() -> WWinPluginAction.refreshActionList());
	}

	protected void saveDialogSettings() {
		if (dialogSettings == null) {
