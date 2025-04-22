	@Test public void shouldOpenCompareEditorInGitChangeSet() throws Exception {
		resetRepository(PROJ1);
		createTag(PROJ1, "compare1");
		changeFilesInProject();
		showDialog(PROJ1, "Team", "Synchronize...");

		bot.shell("Synchronize repository: " + REPO1 + File.separator + ".git")
				.activate();

		bot.comboBox(2)
				.setSelection(UIText.SynchronizeWithAction_tagsName);
		bot.comboBox(3).setSelection("compare1");

		bot.button(IDialogConstants.OK_LABEL).click();

		SWTBotTree syncViewTree = bot.viewByTitle("Synchronize").bot().tree();
		syncViewTree.getAllItems()[0].collapse().doubleClick();
		syncViewTree.getAllItems()[0].getItems()[0].getNode(FOLDER)
				.getNode(FILE1).doubleClick();

		SWTBot compare = bot.editorByTitle(FILE1).bot();
		assertNotNull(compare);
	}

	@Test public void shouldOpenCompareEditorInWorksaceModel() throws Exception {
		resetRepository(PROJ1);
		createTag(PROJ1, "compare2");
		changeFilesInProject();
		showDialog(PROJ1, "Team", "Synchronize...");

		bot.shell("Synchronize repository: " + REPO1 + File.separator + ".git")
				.activate();

		bot.comboBox(2)
				.setSelection(UIText.SynchronizeWithAction_tagsName);
		bot.comboBox(3).setSelection("compare2");

		bot.button(IDialogConstants.OK_LABEL).click();

		SWTBotView syncView = bot.viewByTitle("Synchronize");
		syncView.toolbarDropDownButton("Show File System Resources").click()
				.menuItem("Workspace").click();
		SWTBotTree syncViewTree = syncView.bot().tree();
		syncViewTree.getAllItems()[0].expand().getItems()[0].expand()
				.getItems()[0].doubleClick();

		SWTBot compare = bot.editorByTitle(FILE1).bot();
		assertNotNull(compare);
	}
	
