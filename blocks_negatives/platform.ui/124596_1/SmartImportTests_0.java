		this.dialog = new WizardDialog(getWorkbench().getActiveWorkbenchWindow().getShell(), wizard);
		dialog.setBlockOnOpen(false);
		dialog.open();
		processEvents();
		final Button okButton = getFinishButton(dialog.buttonBar);
		assertNotNull(okButton);
		processEventsUntil(() -> okButton.isEnabled(), -1);
		wizard.performFinish();
		waitForJobs(100, 1000); // give the job framework time to schedule the
		wizard.getImportJob().join();
		waitForJobs(100, 5000); // give some time for asynchronous workspace
