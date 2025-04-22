		IWorkbenchPreferencePage, IExecutableExtension {

	public static final String ACTIVITY_NAME = "activityName"; //$NON-NLS-1$

	public static final String ALLOW_ADVANCED = "allowAdvanced"; //$NON-NLS-1$

	public static final String CAPTION_MESSAGE = "captionMessage"; //$NON-NLS-1$

	public static final String CATEGORY_NAME = "categoryName"; //$NON-NLS-1$

	public static final String ACTIVITY_PROMPT_BUTTON = "activityPromptButton"; //$NON-NLS-1$

	public static final String ACTIVITY_PROMPT_BUTTON_TOOLTIP = "activityPromptButtonTooltip"; //$NON-NLS-1$

	private class AdvancedDialog extends TrayDialog {

		private static final String DIALOG_SETTINGS_SECTION = "ActivityCategoryPreferencePageAdvancedDialogSettings"; //$NON-NLS-1$

		ActivityEnabler enabler;

		protected AdvancedDialog(Shell parentShell) {
			super(parentShell);
