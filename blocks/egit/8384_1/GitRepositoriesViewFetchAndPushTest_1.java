
	private void runFetch(SWTBotTree tree) throws InterruptedException {
		JobJoiner jobJoiner = JobJoiner.startListening(JobFamilies.FETCH, 10, TimeUnit.SECONDS);
		ContextMenuHelper.clickContextMenu(tree, myUtil
				.getPluginLocalizedValue("SimpleFetchCommand"));
		jobJoiner.join();
	}
