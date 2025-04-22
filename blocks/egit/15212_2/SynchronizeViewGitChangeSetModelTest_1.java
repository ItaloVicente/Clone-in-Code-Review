		CompareEditorTester editor = getCompareEditorForNonWorkspaceFileInGitChangeSet(name);

		String left = editor.getLeftEditor().getText();
		String right = editor.getRightEditor().getText();
		assertEquals(content, left);
		assertEquals("", right);
