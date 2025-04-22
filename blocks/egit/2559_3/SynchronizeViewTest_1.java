	@Test public void shouldSynchronizeInEmptyRepository() throws Exception {
		createEmptyRepository();
		showDialog("EmptyProject", "Team", "Synchronize...");

		launchSynchronization("EmptyRepository", null, null, null, null, true);

		SWTBotTree syncViewTree = bot.viewByTitle("Synchronize").bot().tree();
		assertEquals(1, syncViewTree.getAllItems().length);
		SWTBotTreeItem commitTree = syncViewTree.getAllItems()[0];
		commitTree.expand();
		SWTBotTreeItem projectTree = commitTree.getItems()[0];
		projectTree.expand();
		assertEquals(2, projectTree.getItems().length);
		SWTBotTreeItem folderTree = projectTree.getItems()[0];
		folderTree.expand();
		assertEquals(2, folderTree.getItems().length);
		SWTBotTreeItem fileTree = folderTree.getItems()[0];
		assertEquals("test.txt", fileTree.getText());
		fileTree = folderTree.getItems()[1];
		assertEquals("test2.txt", fileTree.getText());
	}

