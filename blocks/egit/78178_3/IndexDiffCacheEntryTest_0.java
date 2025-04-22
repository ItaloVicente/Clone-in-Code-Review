	@Test
	public void testProjectDeletion() throws Exception {
		prepareCacheEntry();

		testRepository.connect(project.project);
		waitForJobs(MAX_WAIT_TIME, JobFamilies.INDEX_DIFF_CACHE_UPDATE);

		String projectName = project.project.getName();
		assertTrue(containsItemsStartingWith(
				entry.getIndexDiff().getUntracked(), projectName + '/'));

		project.project.delete(true, null);
		waitForJobs(MAX_WAIT_TIME, JobFamilies.INDEX_DIFF_CACHE_UPDATE);
		assertFalse(containsItemsStartingWith(
				entry.getIndexDiff().getUntracked(), projectName + '/'));
	}

