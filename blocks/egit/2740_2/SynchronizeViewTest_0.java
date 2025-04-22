	@Test public void shouldShowNonWorkspaceFileInSynchronization()
			throws Exception {
		String name = "non-workspace.txt";
		File root = new File(getTestDirectory(), REPO1);
		File nonWorkspace = new File(root, name);
		BufferedWriter writer = new BufferedWriter(new FileWriter(nonWorkspace));
		writer.append("file content");
		writer.close();

		launchSynchronization(SynchronizeWithAction_tagsName, INITIAL_TAG,
				SynchronizeWithAction_localRepoName, HEAD, true);

		SWTBotTree syncViewTree = bot.viewByTitle("Synchronize").bot().tree();
		SWTBotTreeItem workingTree = syncViewTree
				.expandNode(UIText.GitModelWorkingTree_workingTree);
		assertEquals(1, syncViewTree.getAllItems().length);
		assertEquals(1, workingTree.getNodes(name).size());
	}

	@Test public void shouldShowCompareEditorForNonWorkspaceFileFromSynchronization()
			throws Exception {
		String content = "file content";
		String name = "non-workspace.txt";
		File root = new File(getTestDirectory(), REPO1);
		File nonWorkspace = new File(root, name);
		BufferedWriter writer = new BufferedWriter(new FileWriter(nonWorkspace));
		writer.append(content);
		writer.close();

		launchSynchronization(SynchronizeWithAction_tagsName, INITIAL_TAG,
				SynchronizeWithAction_localRepoName, HEAD, true);

		SWTBotTree syncViewTree = bot.viewByTitle("Synchronize").bot().tree();
		SWTBotTreeItem workingTree = syncViewTree
				.expandNode(UIText.GitModelWorkingTree_workingTree);
		assertEquals(1, syncViewTree.getAllItems().length);
		workingTree.expand().getNode(name).doubleClick();

		SWTBotEditor editor = bot.editorByTitle(name);
		editor.setFocus();

		assertNotNull(editor);
		assertThat(editor.bot().styledText(1).getText(), equalTo(content));
	}

