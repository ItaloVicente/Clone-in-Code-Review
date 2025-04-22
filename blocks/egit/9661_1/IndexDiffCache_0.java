	public void dispose() {
		for (IndexDiffCacheEntry entry : entries.values())
			entry.dispose();
		Job.getJobManager().cancel(JobFamilies.INDEX_DIFF_CACHE_UPDATE);
	}

