	@Test
	public void shouldRefreshSyncResultAfterWorkspaceChange() throws Exception {
		String newFileName = "new.txt";
		resetRepositoryToCreateInitialTag();
		launchSynchronization(INITIAL_TAG, HEAD, true);
		IProject proj = ResourcesPlugin.getWorkspace().getRoot()
				.getProject(PROJ1);

		IFile newFile = proj.getFile(newFileName);
		newFile.create(
				new ByteArrayInputStream("content of new file".getBytes(proj
						.getDefaultCharset())), false, null);
		Job.getJobManager().join(ResourcesPlugin.FAMILY_AUTO_BUILD, null);

		SWTBotTree syncViewTree = bot.viewByTitle("Synchronize").bot().tree();
		SWTBotTreeItem[] syncItems = syncViewTree.getAllItems();
		assertTrue(syncItems[0].getText().contains(PROJ1));
		syncItems[0].expand();
		assertNotNull(syncItems[0].getNode(newFileName));
	}

	@Test
	public void shouldRefreshSyncResultAfterRepositoryChange() throws Exception {
		resetRepositoryToCreateInitialTag();
		changeFilesInProject();
		launchSynchronization(HEAD, HEAD, true);

		SWTBotTree syncViewTree = bot.viewByTitle("Synchronize").bot().tree();
		SWTBotTreeItem[] syncItems = syncViewTree.getAllItems();
		assertTrue(syncItems[0].getText().contains(PROJ1));
		syncItems[0].expand();
		syncItems[0].getItems()[0].expand();
		assertEquals(2, syncItems[0].getItems()[0].getItems().length);

		commit(PROJ1);

		SWTBot viewBot = bot.viewByTitle("Synchronize").bot();
		@SuppressWarnings("unchecked")
		Matcher matcher = allOf(widgetOfType(Label.class),
				withRegex("No changes in .*"));

		@SuppressWarnings("unchecked")
		SWTBotLabel l = new SWTBotLabel((Label) viewBot.widget(matcher));
		assertNotNull(l);
	}

