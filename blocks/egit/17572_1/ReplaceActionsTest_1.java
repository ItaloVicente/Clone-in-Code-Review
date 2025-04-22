
	private void executeReplace(SWTBotShell dialog) {
		JobJoiner jobJoiner = JobJoiner.startListening(
				JobFamilies.DISCARD_CHANGES, 30, TimeUnit.SECONDS);
		dialog.bot().button(IDialogConstants.OK_LABEL).click();
		jobJoiner.join();
	}
