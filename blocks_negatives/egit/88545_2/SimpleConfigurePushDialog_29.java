	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText(UIText.SimpleConfigurePushDialog_WindowTitle);
	}

	@Override
	public void buttonPressed(int buttonId) {
		if (buttonId == DRY_RUN) {
			try {
				new ProgressMonitorDialog(getShell()).run(true, true,
						new IRunnableWithProgress() {
							@Override
							public void run(IProgressMonitor monitor)
									throws InvocationTargetException,
									InterruptedException {
								final PushOperationUI op = new PushOperationUI(
										repository, config, true);
								try {
									final PushOperationResult result = op
											.execute(monitor);
									getShell().getDisplay().asyncExec(() -> {
										PushResultDialog dlg = new PushResultDialog(
												getShell(), repository, result,
												op.getDestinationString(), true,
												PushMode.UPSTREAM);
										dlg.showConfigureButton(false);
										dlg.open();
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
				config = new RemoteConfig(repository.getConfig(),
						config.getName());
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
									PushOperationUI op = new PushOperationUI(
											repository, config.getName(),
											false);
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
