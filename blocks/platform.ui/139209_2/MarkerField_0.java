	private boolean helpContextAvailabilityCheck;

	public MarkerField() {
		helpContextAvailabilityCheck = Platform.getPreferencesService().getBoolean(IDEWorkbenchPlugin.IDE_WORKBENCH,
				IDEInternalPreferences.HELP_CONTEXT_AVAILABILITY_CHECK, true, null); // $NON-NLS-1$
	}
