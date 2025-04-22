
	private void runPush(SWTBotTree tree) {
		JobJoiner jobJoiner = JobJoiner.startListening(JobFamilies.PUSH, 10, TimeUnit.SECONDS);
		ContextMenuHelper.clickContextMenu(tree, myUtil
				.getPluginLocalizedValue("SimplePushCommand"));
		jobJoiner.join();
	}

	private void runFetch(SWTBotTree tree) {
		JobJoiner jobJoiner = JobJoiner.startListening(JobFamilies.FETCH, 10, TimeUnit.SECONDS);
		ContextMenuHelper.clickContextMenu(tree, myUtil
				.getPluginLocalizedValue("SimpleFetchCommand"));
		jobJoiner.join();
	}
