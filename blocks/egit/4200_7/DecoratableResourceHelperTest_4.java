		
		indexDiffCacheEntry = Activator.getDefault().getIndexDiffCache().getIndexDiffCacheEntry(repository);
		TestUtil.joinJobs(JobFamilies.INDEX_DIFF_CACHE_UPDATE);
	}

	private void waitForIndexDiff() throws Exception {
		indexDiffCacheEntry.refresh();
		TestUtil.joinJobs(JobFamilies.INDEX_DIFF_CACHE_UPDATE);
