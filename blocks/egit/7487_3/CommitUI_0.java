	private void pushWhenFinished(Job commitJob) {
		commitJob.addJobChangeListener(new JobChangeAdapter() {

			@Override
			public void done(IJobChangeEvent event) {
				RemoteConfig config = SimpleConfigurePushDialog
						.getConfiguredRemote(repo);
				if (config == null) {
					Display.getDefault().syncExec(new Runnable() {

						public void run() {
							try {
								WizardDialog wizardDialog = new WizardDialog(shell,
									new PushWizard(repo));
								wizardDialog.setHelpAvailable(true);
								wizardDialog.open();
							} catch (URISyntaxException e) {
								Activator.handleError(e.getMessage(), e, true);
							}
						}
					});
				} else {
					int timeout = Activator.getDefault().getPreferenceStore()
							.getInt(UIPreferences.REMOTE_CONNECTION_TIMEOUT);
					PushOperationUI op = new PushOperationUI(repo, config.getName(),
							timeout, false);
					op.start();
				}
			}});
	}

