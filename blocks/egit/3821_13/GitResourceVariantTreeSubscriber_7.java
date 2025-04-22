	public void init(IProgressMonitor monitor) {
		monitor.beginTask(
				CoreText.GitResourceVariantTreeSubscriber_fetchTaskName,
				gsds.size());
		try {
			cache = GitSyncCache.getAllData(gsds, monitor);
		} finally {
			monitor.done();
		}
	}
