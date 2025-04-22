		if (pushTo == PushMode.GERRIT || config == null) {
			final Display display = PlatformUI.getWorkbench().getDisplay();
			display.asyncExec(new Runnable() {

				@Override
				public void run() {
					Wizard pushWizard = getPushWizard(commit, pushTo);
					if (pushWizard != null) {
						WizardDialog wizardDialog = new WizardDialog(
								display.getActiveShell(), pushWizard);
						wizardDialog.setHelpAvailable(true);
						wizardDialog.open();
					}
				}
			});
		} else {
			PushOperationUI op = new PushOperationUI(repository,
					config.getName(), false);
			op.start();
		}
	}

	private Wizard getPushWizard(final RevCommit commit,
			final PushMode pushTo) {
		switch (pushTo) {
		case GERRIT:
			return new PushToGerritWizard(repository);
		case UPSTREAM:
