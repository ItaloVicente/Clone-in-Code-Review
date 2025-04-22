	private static final String DIALOG_SETTINGS_SECTION_NAME = Activator.PLUGIN_ID
			+ ".COMMIT_MESSAGE_EDITOR_DIALOG_SECTION"; //$NON-NLS-1$

	private static final Pattern CHANGE_ID = Pattern
			.compile("^Change-Id: I[0-9a-fA-F]{40}$", Pattern.MULTILINE); //$NON-NLS-1$

	private Repository repository;
