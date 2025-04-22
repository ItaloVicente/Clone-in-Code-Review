	@Test
	public void testCompareIndexWithHeadEnablement() throws Exception {
		String compareIndexWithHead = util.getPluginLocalizedValue("CompareIndexWithHeadAction_label");
		String filePath = FOLDER + '/' + FILE1;
		touch(PROJ_A, filePath, "Changed content");
		SWTBotTree projectExplorerTree = TestUtil.getExplorerTree();
		SWTBotTreeItem fileItem = TestUtil.navigateTo(projectExplorerTree,
				PROJ_A, FOLDER, FILE1);
		fileItem.select();
		assertTrue("Compare With->Index with HEAD should be disabled or absent",
				!ContextMenuHelper.contextMenuItemExists(projectExplorerTree,
						"Compare With", compareIndexWithHead)
						|| !ContextMenuHelper.isContextMenuItemEnabled(
								projectExplorerTree, "Compare With",
								compareIndexWithHead));
		ContextMenuHelper.clickContextMenuSync(projectExplorerTree, "Team",
				addToIndexLabel);
		TestUtil.joinJobs(ADD_TO_INDEX);
		TestUtil.joinJobs(JobFamilies.INDEX_DIFF_CACHE_UPDATE);
		StagingUtil.assertStaging(PROJ_A, filePath, true);
		assertTrue("Compare With->Index with HEAD should be enabled",
				ContextMenuHelper.isContextMenuItemEnabled(projectExplorerTree,
						"Compare With", compareIndexWithHead));
		ContextMenuHelper.clickContextMenuSync(projectExplorerTree, "Team",
				removeFromIndexLabel);
		TestUtil.joinJobs(REMOVE_FROM_INDEX);
		TestUtil.joinJobs(JobFamilies.INDEX_DIFF_CACHE_UPDATE);
		StagingUtil.assertStaging(PROJ_A, filePath, false);
		assertTrue(
				"Compare With->Index with HEAD should be disabled or absent again",
				!ContextMenuHelper.contextMenuItemExists(projectExplorerTree,
						"Compare With", compareIndexWithHead)
						|| !ContextMenuHelper.isContextMenuItemEnabled(
								projectExplorerTree, "Compare With",
								compareIndexWithHead));
	}
