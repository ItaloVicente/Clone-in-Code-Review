		Repository[] repositories = org.eclipse.egit.core.Activator.getDefault()
				.getRepositoryCache().getAllRepositories();
		assertEquals("Expected no cached repositories", 0, repositories.length);
		IndexDiffCache indexDiffCache = org.eclipse.egit.core.Activator
				.getDefault().getIndexDiffCache();
		assertTrue("Expected no IndexDiffCache entries",
				indexDiffCache.currentCacheEntries().isEmpty());
