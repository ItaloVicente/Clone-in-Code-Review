		SWTBotEditor editor = getCompareEditorForNonWorkspaceFileInGitChangeSet(name);
		editor.setFocus();

		SWTBotStyledText left = editor.bot().styledText(content);
		SWTBotStyledText right = editor.bot().styledText("");
		assertNotSame(left, right);
