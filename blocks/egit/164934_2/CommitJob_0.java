		final RemoteConfig config = SimpleConfigurePushDialog
				.getConfiguredRemote(repository);

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
