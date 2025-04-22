	private SWTBotTable getFileDiffTable() throws Exception {
		Job.getJobManager().join(JobFamilies.HISTORY_DIFF, null);
		Thread.sleep(100);
		PlatformUI.getWorkbench().getDisplay().syncExec(() -> {
		return getHistoryViewBot().table(1);
	}

