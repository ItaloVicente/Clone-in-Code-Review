	private void joinRepoViewRefresh() throws Exception {
		TestUtil.joinJobs(JobFamilies.REPO_VIEW_REFRESH);
		PlatformUI.getWorkbench().getDisplay().syncExec(new Runnable() {
			public void run() {
			}
		});
	}

