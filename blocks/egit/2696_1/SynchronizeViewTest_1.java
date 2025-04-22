	@Test public void shouldNotShowIgnoredFilesInGitChangeSetModel()
			throws Exception {
		resetRepositoryToCreateInitialTag();
		String ignoredName = "to-be-ignored.txt";

		IProject proj = ResourcesPlugin.getWorkspace().getRoot()
				.getProject(PROJ1);

		IFile ignoredFile = proj.getFile(ignoredName);
		ignoredFile.create(new ByteArrayInputStream("content of ignored file"
				.getBytes(proj.getDefaultCharset())), false, null);

		IFile gitignore = proj.getFile(".gitignore");
		gitignore.create(
				new ByteArrayInputStream(ignoredName.getBytes(proj
						.getDefaultCharset())), false, null);
		proj.refreshLocal(IResource.DEPTH_INFINITE, null);

		launchSynchronization(SynchronizeWithAction_tagsName, INITIAL_TAG,
				SynchronizeWithAction_localRepoName, HEAD, true);

		SWTBotTree syncViewTree = bot.viewByTitle("Synchronize").bot().tree();
		syncViewTree.expandNode(UIText.GitModelWorkingTree_workingTree);
		assertEquals(1, syncViewTree.getAllItems().length);
		SWTBotTreeItem proj1Node = syncViewTree.getAllItems()[0];
		proj1Node.getItems()[0].expand();
		assertEquals(1, proj1Node.getItems()[0].getItems().length);

		syncViewTree = setPresentationModel("Workspace").tree();
		SWTBotTreeItem projectTree = waitForNodeWithText(syncViewTree, PROJ1);
		projectTree.expand();
		assertEquals(1, projectTree.getItems().length);
	}

