	@Test
	public void shouldRefreshSyncResultAfterWorkspaceChange() throws Exception {
		String newFileName = "new.txt";
		resetRepositoryToCreateInitialTag();
		launchSynchronization(INITIAL_TAG, HEAD, true);
		setGitChangeSetPresentationModel();
		IProject proj = ResourcesPlugin.getWorkspace().getRoot()
				.getProject(PROJ1);

		IFile newFile = proj.getFile(newFileName);
		newFile.create(
				new ByteArrayInputStream("content of new file".getBytes(proj
						.getDefaultCharset())), false, null);
		proj.refreshLocal(IResource.DEPTH_INFINITE, null);
		Job.getJobManager().join(ResourcesPlugin.FAMILY_AUTO_BUILD, null);

		SWTBotTree syncViewTree = bot.viewByTitle("Synchronize").bot().tree();
		SWTBotTreeItem[] syncItems = syncViewTree.getAllItems();
		assertEquals(GitModelWorkingTree_workingTree, syncItems[0].getText());
		syncItems[0].doubleClick(); // expand all
		assertNotNull(syncItems[0].getNode(PROJ1));
		assertNotNull(syncItems[0].getNode(PROJ1).getNode(newFileName));
	}

	@Test
	public void shouldRefreshSyncResultAfterRepositoryChange() throws Exception {
		resetRepositoryToCreateInitialTag();
		changeFilesInProject();
		launchSynchronization(HEAD, HEAD, true);
		setGitChangeSetPresentationModel();

		SWTBotTree syncViewTree = bot.viewByTitle("Synchronize").bot().tree();
		SWTBotTreeItem[] syncItems = syncViewTree.getAllItems();
		assertEquals(GitModelWorkingTree_workingTree, syncItems[0].getText());
		syncItems[0].doubleClick();
		assertEquals(2,
				syncItems[0].getItems()[0].getItems()[0].getItems().length);

		commit(PROJ1);

		SWTBot viewBot = bot.viewByTitle("Synchronize").bot();
		@SuppressWarnings("unchecked")
		Matcher matcher = allOf(widgetOfType(Label.class),
				withRegex("No changes in .*"));

		@SuppressWarnings("unchecked")
		SWTBotLabel l = new SWTBotLabel((Label) viewBot.widget(matcher));
		assertNotNull(l);
	}

