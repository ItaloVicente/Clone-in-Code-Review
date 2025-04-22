	private Composite createMainPanel(final Composite parent) {
		topControl = new Composite(parent, SWT.NONE);
		StackLayout layout = new StackLayout();
		topControl.setLayout(layout);
	
		final Composite c = new Composite(topControl, SWT.NULL);
		layout.topControl = c;
		errorText = new StyledText(topControl, SWT.NONE);
		errorText.setFont(UIUtils
				.getFont(UIPreferences.THEME_CommitMessageFont));
		errorText.setText(UIText.CommitFileDiffViewer_SelectOneCommitMessage);
	
		final GridLayout parentLayout = new GridLayout();
		parentLayout.marginHeight = 0;
		parentLayout.marginWidth = 0;
		parentLayout.verticalSpacing = 0;
		c.setLayout(parentLayout);
		return c;
