		Job.getJobManager().join(JobFamilies.GENERATE_HISTORY, null);
		projectExplorerTree.widget.getDisplay().syncExec(new Runnable() {
			@Override
			public void run() {
			}
		});

		return getHistoryViewBot().table();
