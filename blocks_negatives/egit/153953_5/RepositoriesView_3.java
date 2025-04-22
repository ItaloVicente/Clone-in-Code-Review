	private void schedule(Job job, long delay) {
		IWorkbenchSiteProgressService service = CommonUtils.getService(getSite(), IWorkbenchSiteProgressService.class);

		if (GitTraceLocation.REPOSITORIESVIEW.isActive()) {
			GitTraceLocation.getTrace().trace(
					GitTraceLocation.REPOSITORIESVIEW.getLocation(),
		}
		service.schedule(job, delay);
	}

	private void unregisterRepositoryListener() {
		for (ListenerHandle lh : myListeners)
			lh.remove();
