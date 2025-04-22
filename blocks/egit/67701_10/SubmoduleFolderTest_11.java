	@Test
	public void testRepoRemoval() {
		Activator.getDefault().getRepositoryUtil()
				.addConfiguredRepository(childRepositoryGitDir);
		assertTrue("Should still have the subrepo in the cache",
				containsRepo(Activator.getDefault().getRepositoryCache()
						.getAllRepositories(), subRepository));
		assertTrue("Should have changed the preference", Activator.getDefault()
				.getRepositoryUtil().removeDir(childRepositoryGitDir));
		assertTrue("Should still have the subrepo in the cache",
				containsRepo(Activator.getDefault().getRepositoryCache()
						.getAllRepositories(), subRepository));
	}

