		waitForDecorations();
		closeGitViews();
		TestUtil.waitForJobs(500, 5000);
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		IProject project = root.getProject(PROJ1);
		for (int i = 0; i < 101; i++) {
			project.create(null);
			if (i == 0) {
				SWTBotTree explorerTree = TestUtil.getExplorerTree();
				SWTBotTreeItem projectNode = TestUtil.navigateTo(explorerTree,
						PROJ1);
				projectNode.select();
				ContextMenuHelper.isContextMenuItemEnabled(explorerTree, "New");
			}
			project.delete(true, true, null);
		}
		TestUtil.joinJobs(
				org.eclipse.egit.core.JobFamilies.INDEX_DIFF_CACHE_UPDATE);
		waitForDecorations();
		TestUtil.waitForJobs(500, 5000);
