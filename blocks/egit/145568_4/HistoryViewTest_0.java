	private SWTBotTable getHistoryViewTable() throws Exception {
		SWTBot historyView = getHistoryViewBot();
		Job.getJobManager().join(JobFamilies.GENERATE_HISTORY, null);
		historyView.getDisplay().syncExec(() -> {
		});
		return historyView.table();
	}

