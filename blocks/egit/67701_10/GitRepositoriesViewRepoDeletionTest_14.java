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
		TestUtil.waitForJobs(500, 5000);
		final String[] results = { null, null };
		Job verifier = new Job(testName.getMethodName()) {

			@Override
			protected IStatus run(IProgressMonitor monitor) {
				try {
					TestUtil.joinJobs(
							org.eclipse.egit.core.JobFamilies.INDEX_DIFF_CACHE_UPDATE);
					TestUtil.joinJobs(JobFamilies.REPO_VIEW_REFRESH);
					waitForDecorations();
				} catch (InterruptedException e) {
					results[0] = "Interrupted";
					Thread.currentThread().interrupt();
					return Status.CANCEL_STATUS;
				}
				waitForFinalization(5000);
				Repository[] repositories = org.eclipse.egit.core.Activator
						.getDefault().getRepositoryCache().getAllRepositories();
				results[0] = Arrays.asList(repositories).toString();
				IndexDiffCache indexDiffCache = org.eclipse.egit.core.Activator
						.getDefault().getIndexDiffCache();
				results[1] = indexDiffCache.currentCacheEntries().toString();
				return Status.OK_STATUS;
			}

		};
		verifier.setRule(new RepositoryCacheRule());
		verifier.setSystem(true);
		verifier.schedule();
		verifier.join();
