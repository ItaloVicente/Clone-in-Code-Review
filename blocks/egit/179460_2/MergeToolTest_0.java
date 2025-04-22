
	@Test
	public void useHeadOptionOpenedAgainShouldHaveEdits() throws Exception {
		org.eclipse.egit.ui.Activator.getDefault().getPreferenceStore()
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
