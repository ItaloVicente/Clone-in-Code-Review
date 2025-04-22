		jobSchedulingAssertion
				.assertScheduled("Delete of file outside of workspace should have cause an index diff cache job.");
	}

	private static void initIndexDiffCache(Repository repository)
			throws Exception {
		IndexDiffCache cache = Activator.getDefault().getIndexDiffCache();
		IndexDiffCacheEntry cacheEntry = cache.getIndexDiffCacheEntry(repository);
		assertNotNull(cacheEntry);
		Job.getJobManager().join(JobFamilies.INDEX_DIFF_CACHE_UPDATE, null);
