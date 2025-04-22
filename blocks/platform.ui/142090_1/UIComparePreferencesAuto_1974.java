	private PreferenceDialog getPreferenceDialog(String id) {
		PreferenceDialogWrapper dialog = null;
		PreferenceManager manager = WorkbenchPlugin.getDefault()
				.getPreferenceManager();
		if (manager != null) {
			dialog = new PreferenceDialogWrapper(getShell(), manager);
			dialog.create();
			PlatformUI.getWorkbench().getHelpSystem().setHelp(dialog.getShell(),
					IWorkbenchHelpContextIds.PREFERENCE_DIALOG);
