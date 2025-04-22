		SWTBotShell fetchDialog = openFetchDialog();
		fetchDialog.bot().button(IDialogConstants.NEXT_LABEL).click();
		JobJoiner jobJoiner = JobJoiner.startListening(JobFamilies.FETCH, 20, TimeUnit.SECONDS);
		fetchDialog.bot().button(IDialogConstants.FINISH_LABEL).click();
		jobJoiner.join();
	}

	private SWTBotShell openFetchDialog() throws Exception {
