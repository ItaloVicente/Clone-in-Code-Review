	private boolean signedOff = org.eclipse.egit.ui.Activator.getDefault()
	.getPreferenceStore()
	.getBoolean(UIPreferences.COMMIT_DIALOG_SIGNED_OFF_BY);

	private boolean amending = false;

	private boolean amendAllowed = true;

