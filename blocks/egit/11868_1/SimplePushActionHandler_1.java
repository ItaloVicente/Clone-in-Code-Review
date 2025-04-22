	public static void pushOrConfigure(final Repository repository,
			RemoteConfig config, Shell shell) {
		if (config != null) {
			int timeout = Activator.getDefault().getPreferenceStore().getInt(
					UIPreferences.REMOTE_CONNECTION_TIMEOUT);
			PushOperationUI op = new PushOperationUI(repository, config.getName(), timeout,
					false);
			op.start();
		} else {
			Ref head = getHeadIfSymbolic(repository);
			if (head != null) {
				PushBranchWizard pushBranchWizard = new PushBranchWizard(
						repository, head.getTarget());

				WizardDialog dlg = new WizardDialog(shell,
						pushBranchWizard);
				dlg.open();
			}
		}
	}

