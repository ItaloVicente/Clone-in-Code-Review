	protected void createButtonsForButtonBar(Composite parent) {
		Button cancelButton = createButton(parent, IDialogConstants.ABORT_ID, "Cancel user operation", false); //$NON-NLS-1$
		cancelButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				blockingMonitor.clearBlocked();
				blockingMonitor.setCanceled(true);
			}

			@Override
			public void widgetSelected(SelectionEvent e) {
				blockingMonitor.clearBlocked();
				blockingMonitor.setCanceled(true);
			}
		});
