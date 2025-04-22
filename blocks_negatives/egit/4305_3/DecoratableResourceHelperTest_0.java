	private void waitForIndexDiffUpdate(final boolean refreshCache) throws Exception {
		ModalContext.run(new IRunnableWithProgress() {

			public void run(IProgressMonitor monitor) throws InvocationTargetException,
					InterruptedException {
				if (refreshCache)
					indexDiffCacheEntry.refresh();
				TestUtil.joinJobs(JobFamilies.INDEX_DIFF_CACHE_UPDATE);
			}
		}, true, new NullProgressMonitor(), PlatformUI.getWorkbench().getDisplay());
