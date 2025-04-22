	@Test
	public void testRepoViewFollowSelection() throws Exception {
		SWTBotView view = TestUtil.showView(RepositoriesView.VIEW_ID);
		TestUtil.joinJobs(REPO_VIEW_REFRESH);
		view.toolbarButton(
				UTIL.getPluginLocalizedValue("LinkWithSelectionCommand"))
				.click();
		try {
			SWTBotTree projectExplorerTree = TestUtil.getExplorerTree();
			SWTBotTreeItem node = TestUtil.navigateTo(projectExplorerTree,
					childFolder.getFullPath().segments());
			node.select();
			TestUtil.waitForDecorations();
			TestUtil.joinJobs(REPO_VIEW_REFRESH);
			SWTBotTree tree = view.bot().tree();
			int[] numberOfSelected = { 0 };
			boolean[] parentFound = { false };
			view.getWidget().getDisplay().syncExec(() -> {
				Tree t = tree.widget;
				TreeItem[] selected = t.getSelection();
				numberOfSelected[0] = selected.length;
				if (selected.length == 1) {
					TreeItem root = null;
					TreeItem parent = selected[0].getParentItem();
					String parentRepoName = parentRepositoryGitDir
							.getParentFile().getName();
					while (parent != null) {
						root = parent;
						parent = parent.getParentItem();
					}
					if (root != null
							&& root.getText().startsWith(parentRepoName)) {
						parentFound[0] = true;
					}
				}
			});
			assertEquals("One node selected", 1, numberOfSelected[0]);
			assertTrue("Selected node not under parent repository",
					parentFound[0]);
		} finally {
			view.toolbarButton(
					UTIL.getPluginLocalizedValue("LinkWithSelectionCommand"))
					.click();
		}
	}

