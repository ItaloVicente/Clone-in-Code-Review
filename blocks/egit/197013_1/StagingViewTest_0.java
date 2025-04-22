	@Test
	public void testCompareRenamedFileStaged() throws Exception {
		IFile file = ResourcesPlugin.getWorkspace().getRoot().getProject(PROJ1)
				.getFolder(FOLDER).getFile(FILE1);
		String moved = "moved.txt";
		String movedPath = PROJ1 + '/' + FOLDER + '/' + moved;
		try (Git git = new Git(repository)) {
			assertTrue(file.exists());
			file.move(file.getParent().getFullPath().append(moved), true,
					null);
			assertFalse(file.exists());
			git.rm().addFilepattern(FILE1_PATH).call();
			git.add().addFilepattern(movedPath).call();
			TestUtil.joinJobs(JobFamilies.INDEX_DIFF_CACHE_UPDATE);
		}
		StagingViewTester stagingView = StagingViewTester.openStagingView();
		SWTBotView view = stagingView.getView();
		SWTBot viewBot = view.bot();
		SWTBotTree stagedTree = viewBot.tree(1);
		TestUtil.waitUntilTreeHasNodeContainsText(viewBot, stagedTree,
				movedPath, 10000);
		SWTBotTreeItem item = TestUtil.getNode(stagedTree.getAllItems(),
				movedPath);
		item.select();
		ContextMenuHelper.clickContextMenu(stagedTree,
				UIText.StagingView_CompareWithHeadMenuLabel);

		CompareEditorTester compareEditor = CompareEditorTester
				.forTitleContaining("Compare " + moved);
		String leftText = compareEditor.getLeftEditor().getText();
		String rightText = compareEditor.getRightEditor().getText();
		assertEquals(leftText, rightText);
	}

	@Test
	public void testCompareCopiedFileStaged() throws Exception {
		IFile file = ResourcesPlugin.getWorkspace().getRoot().getProject(PROJ1)
				.getFolder(FOLDER).getFile(FILE1);
		String moved = "moved.txt";
		String movedPath = PROJ1 + '/' + FOLDER + '/' + moved;
		try (Git git = new Git(repository)) {
			assertTrue(file.exists());
			file.move(file.getParent().getFullPath().append(moved), true, null);
			assertFalse(file.exists());
			touch("New content");
			assertTrue(file.exists());
			git.add().addFilepattern(movedPath).addFilepattern(FILE1_PATH)
					.call();
			TestUtil.joinJobs(JobFamilies.INDEX_DIFF_CACHE_UPDATE);
		}
		StagingViewTester stagingView = StagingViewTester.openStagingView();
		SWTBotView view = stagingView.getView();
		SWTBot viewBot = view.bot();
		SWTBotTree stagedTree = viewBot.tree(1);
		TestUtil.waitUntilTreeHasNodeContainsText(viewBot, stagedTree,
				movedPath, 10000);
		SWTBotTreeItem item = TestUtil.getNode(stagedTree.getAllItems(),
				movedPath);
		item.select();
		ContextMenuHelper.clickContextMenu(stagedTree,
				UIText.StagingView_CompareWithHeadMenuLabel);

		CompareEditorTester compareEditor = CompareEditorTester
				.forTitleContaining("Compare " + moved);
		String leftText = compareEditor.getLeftEditor().getText();
		String rightText = compareEditor.getRightEditor().getText();
		assertEquals(leftText, rightText);
		item = TestUtil.getNode(stagedTree.getAllItems(), FILE1_PATH);
		item.select();
		ContextMenuHelper.clickContextMenu(stagedTree,
				UIText.StagingView_CompareWithHeadMenuLabel);

		compareEditor = CompareEditorTester
				.forTitleContaining("Compare " + FILE1);
		leftText = compareEditor.getLeftEditor().getText();
		assertEquals("New content", leftText);
	}

