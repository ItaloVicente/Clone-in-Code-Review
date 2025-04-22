		layout.topControl = c;
		errorText = new StyledText(parent, SWT.NONE);
		errorText.setFont(UIUtils
				.getFont(UIPreferences.THEME_CommitMessageFont));
		errorText.setText(UIText.CommitFileDiffViewer_SelectOneCommitMessage);

