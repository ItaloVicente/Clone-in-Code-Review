
	@Test
	public void useHeadOptionOpenedAgainShouldHaveEdits() throws Exception {
		Activator.getDefault().getPreferenceStore()
				.setValue(UIPreferences.MERGE_MODE, 2);
		IPath path = new Path(PROJ1).append("folder/test.txt");
		testRepository.branch("stable").commit().add(path.toString(), "stable")
				.create();
		touchAndSubmit("master", "master");
		MergeOperation mergeOp = new MergeOperation(
				testRepository.getRepository(), "stable");
		mergeOp.execute(null);
		MergeResult mergeResult = mergeOp.getResult();
		assertThat(mergeResult.getMergeStatus(), is(MergeStatus.CONFLICTING));
		assertThat(mergeResult.getConflicts().keySet(),
				hasItem(path.toString()));

		IndexDiffCache cache = IndexDiffCache.getInstance();
		cache.getIndexDiffCacheEntry(testRepository.getRepository());
		TestUtil.joinJobs(JobFamilies.INDEX_DIFF_CACHE_UPDATE);

		SWTBotTree packageExplorer = TestUtil.getExplorerTree();
		SWTBotTreeItem project1 = getProjectItem(packageExplorer, PROJ1)
				.select();

		SWTBotTreeItem folderNode = TestUtil.expandAndWait(project1)
				.getNode(FOLDER);
		SWTBotTreeItem fileNode = TestUtil.expandAndWait(folderNode)
				.getNode(FILE1);
		fileNode.select();
		ContextMenuHelper.clickContextMenu(packageExplorer,
				util.getPluginLocalizedValue("TeamMenu.label"),
				util.getPluginLocalizedValue("MergeToolAction.label"));

		CompareEditorTester compareEditor = CompareEditorTester
				.forTitleContaining("Merging");

		String text = compareEditor.getLeftEditor().getText();
		assertThat(text, is("master"));
		compareEditor.getLeftEditor().setText("master edited");
		assertTrue(compareEditor.isDirty());
		compareEditor.save();
		compareEditor.close();
		TestUtil.navigateTo(packageExplorer, PROJ1, FOLDER, FILE1).select();
		ContextMenuHelper.clickContextMenu(packageExplorer,
				util.getPluginLocalizedValue("TeamMenu.label"),
				util.getPluginLocalizedValue("MergeToolAction.label"));

		compareEditor = CompareEditorTester.forTitleContaining("Merging");

		text = compareEditor.getLeftEditor().getText();
		assertThat(text, is("master edited"));
	}

	@Test
	public void verifyCherrypickBase() throws Exception {
		Activator.getDefault().getPreferenceStore()
				.setValue(UIPreferences.MERGE_MODE, 2);
		testRepository.branch("stable").commit()
				.add(FILE1_PATH, "stable").create();
		touchAndSubmit("master", "master");
		RevCommit stableTip = testRepository.branch("stable").commit()
				.add(FILE1_PATH, "stable 2").create();
		CherryPickOperation op = new CherryPickOperation(
				testRepository.getRepository(), stableTip);
		op.execute(null);
		CherryPickResult result = op.getResult();

		assertThat(result.getStatus(), is(CherryPickStatus.CONFLICTING));

		IndexDiffCache cache = IndexDiffCache.getInstance();
		cache.getIndexDiffCacheEntry(testRepository.getRepository());
		TestUtil.joinJobs(JobFamilies.INDEX_DIFF_CACHE_UPDATE);

		SWTBotTree packageExplorer = TestUtil.getExplorerTree();
		TestUtil.navigateTo(packageExplorer, PROJ1, FOLDER, FILE1).select();
		ContextMenuHelper.clickContextMenu(packageExplorer,
				util.getPluginLocalizedValue("TeamMenu.label"),
				util.getPluginLocalizedValue("MergeToolAction.label"));

		CompareEditorTester compareEditor = CompareEditorTester
				.forTitleContaining("Merging");

		String text = compareEditor.getLeftEditor().getText();
		assertThat(text, is("master"));
		text = compareEditor.getRightEditor().getText();
		assertThat(text, is("stable 2"));
		text = compareEditor.getAncestorEditor().getText();
		assertThat(text, is("stable"));
	}

	@Test
	public void conflictUnderneathIgnoredFolder() throws Exception {
		Activator.getDefault().getPreferenceStore()
				.setValue(UIPreferences.MERGE_MODE, 2);
		try (Git git = new Git(testRepository.getRepository())) {
			touch(PROJ1, ".gitignore", '/' + FOLDER);
			git.add().addFilepattern(".gitignore").call();
			git.commit().setMessage("Ignoring folder");
		}
		testRepository.branch("stable").commit().add(FILE1_PATH, "stable")
				.create();
		touchAndSubmit("master", "master");
		RevCommit stableTip = testRepository.branch("stable").commit()
				.add(FILE1_PATH, "stable 2").create();
		CherryPickOperation op = new CherryPickOperation(
				testRepository.getRepository(), stableTip);
		op.execute(null);
		CherryPickResult result = op.getResult();

		assertThat(result.getStatus(), is(CherryPickStatus.CONFLICTING));

		IndexDiffCache cache = IndexDiffCache.getInstance();
		cache.getIndexDiffCacheEntry(testRepository.getRepository());
		TestUtil.joinJobs(JobFamilies.INDEX_DIFF_CACHE_UPDATE);

		SWTBotTree packageExplorer = TestUtil.getExplorerTree();
		TestUtil.navigateTo(packageExplorer, PROJ1, FOLDER, FILE1).select();
		ContextMenuHelper.clickContextMenu(packageExplorer,
				util.getPluginLocalizedValue("TeamMenu.label"),
				util.getPluginLocalizedValue("MergeToolAction.label"));

		CompareEditorTester compareEditor = CompareEditorTester
				.forTitleContaining("Merging");

		String text = compareEditor.getLeftEditor().getText();
		assertThat(text, is("master"));
		text = compareEditor.getRightEditor().getText();
		assertThat(text, is("stable 2"));
	}

	@Test
	public void mergedOursVersion() throws Exception {
		Activator.getDefault().getPreferenceStore()
				.setValue(UIPreferences.MERGE_MODE, 3);
		testRepository.branch("stable").commit().add(FILE1_PATH, "stable")
				.create();
		touchAndSubmit("master", "master");
		RevCommit stableTip = testRepository.branch("stable").commit()
				.add(FILE1_PATH, "stable 2").create();
		CherryPickOperation op = new CherryPickOperation(
				testRepository.getRepository(), stableTip);
		op.execute(null);
		CherryPickResult result = op.getResult();

		assertThat(result.getStatus(), is(CherryPickStatus.CONFLICTING));

		IndexDiffCache cache = IndexDiffCache.getInstance();
		cache.getIndexDiffCacheEntry(testRepository.getRepository());
		TestUtil.joinJobs(JobFamilies.INDEX_DIFF_CACHE_UPDATE);

		assertThat(getTestFileContent(),
				is("<<<<<<< master\nmaster\n=======\nstable 2\n>>>>>>> "
						+ stableTip.abbreviate(7).name() + " \n"));

		SWTBotTree packageExplorer = TestUtil.getExplorerTree();
		TestUtil.navigateTo(packageExplorer, PROJ1, FOLDER, FILE1).select();
		ContextMenuHelper.clickContextMenu(packageExplorer,
				util.getPluginLocalizedValue("TeamMenu.label"),
				util.getPluginLocalizedValue("MergeToolAction.label"));

		CompareEditorTester compareEditor = CompareEditorTester
				.forTitleContaining("Merging");

		String text = compareEditor.getLeftEditor().getText();
		assertThat(text, is("master\n"));
		text = compareEditor.getRightEditor().getText();
		compareEditor.getLeftEditor().setText(text);
		compareEditor.save();
		assertThat(getTestFileContent(), is("stable 2"));
	}

	@Test
	public void mergedOursVersionLongerMarkers() throws Exception {
		Activator.getDefault().getPreferenceStore()
				.setValue(UIPreferences.MERGE_MODE, 3);
		testRepository.branch("stable").commit().add(FILE1_PATH, "stable")
				.create();
		touchAndSubmit("master", "master");
		RevCommit stableTip = testRepository.branch("stable").commit()
				.add(FILE1_PATH, "stable 2").create();
		CherryPickOperation op = new CherryPickOperation(
				testRepository.getRepository(), stableTip);
		op.execute(null);
		CherryPickResult result = op.getResult();

		assertThat(result.getStatus(), is(CherryPickStatus.CONFLICTING));

		IndexDiffCache cache = IndexDiffCache.getInstance();
		cache.getIndexDiffCacheEntry(testRepository.getRepository());
		TestUtil.joinJobs(JobFamilies.INDEX_DIFF_CACHE_UPDATE);

		assertThat(getTestFileContent(),
				is("<<<<<<< master\nmaster\n=======\nstable 2\n>>>>>>> "
						+ stableTip.abbreviate(7).name() + " \n"));

		touch("<<<<<<<<< master\nmaster\n=========\nstable 2\n>>>>>>>>> "
				+ stableTip.abbreviate(7).name() + " \n");
		touch(PROJ1, ".gitattributes", "test.txt conflict-marker-size=9");

		SWTBotTree packageExplorer = TestUtil.getExplorerTree();
		TestUtil.navigateTo(packageExplorer, PROJ1, FOLDER, FILE1).select();
		ContextMenuHelper.clickContextMenu(packageExplorer,
				util.getPluginLocalizedValue("TeamMenu.label"),
				util.getPluginLocalizedValue("MergeToolAction.label"));

		CompareEditorTester compareEditor = CompareEditorTester
				.forTitleContaining("Merging");

		String text = compareEditor.getLeftEditor().getText();
		assertThat(text, is("master\n"));
		text = compareEditor.getRightEditor().getText();
		compareEditor.getLeftEditor().setText(text);
		compareEditor.save();
		assertThat(getTestFileContent(), is("stable 2"));
	}
