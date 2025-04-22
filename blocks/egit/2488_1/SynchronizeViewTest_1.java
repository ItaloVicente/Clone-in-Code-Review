	@Test
	public void shouldListFileDeletedChange() throws Exception {
		resetRepository(PROJ1);
		createTag(PROJ1, "base");
		deleteFileAndCommit(PROJ1);
		showDialog(PROJ1, "Team", "Synchronize...");

		bot.shell("Synchronize repository: " + REPO1 + File.separator + ".git")
				.activate();

		bot.comboBox(2)
				.setSelection(UIText.SynchronizeWithAction_tagsName);
		bot.comboBox(3).setSelection("base");

		bot.button(IDialogConstants.OK_LABEL).click();

		SWTBotTree syncViewTree = bot.viewByTitle("Synchronize").bot().tree();
		assertEquals(1, syncViewTree.getAllItems().length);
		SWTBotTreeItem commitTree = syncViewTree.getAllItems()[0];
		commitTree.expand();
		SWTBotTreeItem projectTree = commitTree.getItems()[0];
		projectTree.expand();
		assertEquals(1, projectTree.getItems().length);
		SWTBotTreeItem folderTree = projectTree.getItems()[0];
		folderTree.expand();
		assertEquals(1, folderTree.getItems().length);
		SWTBotTreeItem fileTree = folderTree.getItems()[0];
		assertEquals("test.txt", fileTree.getText());
	}

