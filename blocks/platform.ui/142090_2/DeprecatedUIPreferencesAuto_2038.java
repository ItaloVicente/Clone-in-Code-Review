	public DeprecatedUIPreferencesAuto(String name) {
		super(name);
	}

	protected Shell getShell() {
		return DialogCheck.getShell();
	}

	private PreferenceDialog getPreferenceDialog(String id) {
		PreferenceDialogWrapper dialog = null;
		PreferenceManager manager = WorkbenchPlugin.getDefault()
				.getPreferenceManager();
		if (manager != null) {
			dialog = new PreferenceDialogWrapper(getShell(), manager);
			dialog.create();
