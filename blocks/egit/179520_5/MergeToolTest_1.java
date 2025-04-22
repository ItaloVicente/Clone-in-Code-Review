
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
