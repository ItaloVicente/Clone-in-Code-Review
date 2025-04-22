		Job.getJobManager().join(JobFamilies.GENERATE_HISTORY, null);
		projectExplorerTree.widget.getDisplay().syncExec(new Runnable() {

			public void run() {
			}
		});
