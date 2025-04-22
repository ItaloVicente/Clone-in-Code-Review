		final RemoteConfig config = SimpleConfigurePushDialog
				.getConfiguredRemote(repository);
		boolean alwaysShowPushWizard = Activator.getDefault()
				.getPreferenceStore()
				.getBoolean(UIPreferences.ALWAYS_SHOW_PUSH_WIZARD_ON_COMMIT);

		if (alwaysShowPushWizard || pushTo == PushMode.GERRIT
				|| config == null) {
			final Display display = PlatformUI.getWorkbench().getDisplay();
			display.asyncExec(new Runnable() {

				@Override
				public void run() {
					Wizard pushWizard = getPushWizard(commit, pushTo);
					if (pushWizard != null) {
						PushWizardDialog dialog = new PushWizardDialog(
								display.getActiveShell(), pushWizard);
						dialog.open();
					}
				}
			});
		} else {
			PushOperationUI op = new PushOperationUI(repository,
					config.getName(), false);
			op.start();
		}
