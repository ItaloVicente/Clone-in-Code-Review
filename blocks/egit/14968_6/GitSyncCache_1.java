		mergeAllDataIntoCache(updateRequests, monitor, cache);
		return cache;
	}

	public static void mergeAllDataIntoCache(
			Map<GitSynchronizeData, Collection<String>> updateRequests,
			IProgressMonitor monitor, GitSyncCache cache) {
