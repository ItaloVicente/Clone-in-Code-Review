	@Test
	public void testRemoveRepositoryRemoveFromCachesBug483664()
			throws Exception {
		deleteAllProjects();
		assertProjectExistence(PROJ1, false);
		clearView();
		refreshAndWait();

		Activator.getDefault().getRepositoryUtil()
				.addConfiguredRepository(repositoryFile);
		refreshAndWait();
		assertHasRepo(repositoryFile);
		SWTBotTree tree = getOrOpenView().bot().tree();
		tree.getAllItems()[0].select();
		JobJoiner joiner = JobJoiner.startListening(
				JobFamilies.REPOSITORY_DELETE, 10, TimeUnit.SECONDS);
		ContextMenuHelper.clickContextMenu(tree, myUtil.getPluginLocalizedValue(
				REMOVE_REPOSITORY_FROM_VIEW_CONTEXT_MENU_LABEL));
		refreshAndWait();
		joiner.join();
		assertEmpty();
		assertTrue(repositoryFile.exists());
		assertTrue(
				new File(repositoryFile.getParentFile(), PROJ1).isDirectory());
		List<String> configuredRepos = org.eclipse.egit.core.Activator
				.getDefault().getRepositoryUtil().getConfiguredRepositories();
		assertTrue("Expected no configured repositories",
				configuredRepos.isEmpty());
		Repository[] repositories = org.eclipse.egit.core.Activator.getDefault()
				.getRepositoryCache().getAllRepositories();
		assertEquals("Expected no cached repositories", 0, repositories.length);
		IndexDiffCache indexDiffCache = org.eclipse.egit.core.Activator
				.getDefault().getIndexDiffCache();
		assertTrue("Expected no IndexDiffCache entries",
				indexDiffCache.currentCacheEntries().isEmpty());
	}

