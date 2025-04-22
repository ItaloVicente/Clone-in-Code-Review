		Composite main = rawTable.getParent();
		stackLayout = new StackLayout();
		main.setLayout(stackLayout);

		noInputText = new StyledText(main, SWT.NONE);
		noInputText.setFont(UIUtils
				.getFont(UIPreferences.THEME_CommitMessageFont));
		noInputText.setText(UIText.CommitFileDiffViewer_SelectOneCommitMessage);
