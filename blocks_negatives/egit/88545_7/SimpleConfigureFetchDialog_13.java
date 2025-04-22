	protected void buttonPressed(int buttonId) {
		if (buttonId == DRY_RUN) {
			try {
				new ProgressMonitorDialog(getShell()).run(true, true,
						new IRunnableWithProgress() {
							@Override
							public void run(IProgressMonitor monitor)
									throws InvocationTargetException,
									InterruptedException {
								int timeout = Activator
										.getDefault()
										.getPreferenceStore()
										.getInt(UIPreferences.REMOTE_CONNECTION_TIMEOUT);
								final FetchOperationUI op = new FetchOperationUI(
										repository, config, timeout, true);
								try {
									final FetchResult result = op
											.execute(monitor);
									getShell().getDisplay().asyncExec(
											new Runnable() {

												@Override
												public void run() {
													FetchResultDialog dlg;
													dlg = new FetchResultDialog(
															getShell(),
															repository,
															result,
															op.getSourceString());
													dlg.showConfigureButton(false);
													dlg.open();
												}
											});
								} catch (CoreException e) {
									Activator.handleError(e.getMessage(), e,
											true);
								}

							}
						});
			} catch (InvocationTargetException e1) {
				Activator.handleError(e1.getMessage(), e1, true);
			} catch (InterruptedException e1) {
			}
			return;
		}
		if (buttonId == REVERT) {
			try {
				config = new RemoteConfig(repository.getConfig(), config
						.getName());
				updateControls();
			} catch (URISyntaxException e) {
				Activator.handleError(e.getMessage(), e, true);
			}
			return;
		}
		if (buttonId == OK || buttonId == SAVE_ONLY) {
			config.update(repository.getConfig());
			try {
				repository.getConfig().save();
			} catch (IOException e) {
				Activator.handleError(e.getMessage(), e, true);
			}
			GerritDialogSettings.updateRemoteConfig(repository, config);
			if (buttonId == OK)
				try {
					new ProgressMonitorDialog(getShell()).run(true, true,
							new IRunnableWithProgress() {
								@Override
								public void run(IProgressMonitor monitor)
										throws InvocationTargetException,
										InterruptedException {
									int timeout = Activator
											.getDefault()
											.getPreferenceStore()
											.getInt(
													UIPreferences.REMOTE_CONNECTION_TIMEOUT);
									FetchOperationUI op = new FetchOperationUI(
											repository, config, timeout, false);
									op.start();
								}
							});
				} catch (InvocationTargetException e) {
					Activator.handleError(e.getMessage(), e, true);
				} catch (InterruptedException e) {
					Activator.handleError(e.getMessage(), e, true);
				}
			okPressed();
			return;
		}
		super.buttonPressed(buttonId);
	}

	private void doPaste() {
		Clipboard clipboard = new Clipboard(getShell().getDisplay());
