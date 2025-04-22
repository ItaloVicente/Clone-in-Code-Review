	protected void createButtonsForButtonBar(Composite parent) {
		createButton(parent, IDialogConstants.CANCEL_ID, ProgressMessages.BlockedJobsDialog_CancelButtonText, false);
	}

	@Override
	protected void cancelPressed() {
		setReturnCode(CANCEL);
		blockingMonitor.clearBlocked(); // clearBlocked() results in calling close()
		blockingMonitor.setCanceled(true);
