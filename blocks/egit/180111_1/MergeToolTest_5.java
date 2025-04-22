
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
