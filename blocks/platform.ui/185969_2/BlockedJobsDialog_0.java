	protected void createButtonsForButtonBar(Composite parent) {
		Button cancelButton = createButton(parent, IDialogConstants.ABORT_ID,
				ProgressMessages.BlockedJobsDialog_CancelButtonText, false);
		cancelButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				blockingMonitor.clearBlocked();
				blockingMonitor.setCanceled(true);
			}
		});
