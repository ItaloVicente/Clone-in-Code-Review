	@Test
	public void testDecorations() throws Exception {
		IProject project = ResourcesPlugin.getWorkspace().getRoot()
				.getProject(PROJ1);
		RepositoryMapping mapping = RepositoryMapping.getMapping(project);
		assertNotNull(mapping);
		SWTBotTree projectExplorerTree = TestUtil.getExplorerTree();
		TestUtil.navigateTo(projectExplorerTree,
				new Path(FILE1_PATH).segments());
		touch("File modified");
		clickOnDisconnect();
		TestUtil.waitForJobs(500, 5000);
		TestUtil.waitForDecorations();
		assertFalse("Project should not have git decorations",
				getProjectItem(projectExplorerTree, PROJ1).getText()
						.contains("["));
		SWTBotShell connectDialog = openConnectDialog();
		connectDialog.bot().button(IDialogConstants.FINISH_LABEL).click();
		TestUtil.waitForJobs(500, 5000);
		TestUtil.waitForDecorations();
		assertTrue("Project should have git decorations",
				getProjectItem(projectExplorerTree, PROJ1).getText()
						.contains("[FirstRepository"));
		SWTBotTreeItem fileNode = TestUtil.navigateTo(projectExplorerTree,
				new Path(FILE1_PATH).segments());
		assertTrue("File should have git decorations",
				fileNode.getText().startsWith(">"));
	}

