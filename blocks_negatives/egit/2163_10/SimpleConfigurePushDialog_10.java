									PushConfiguredRemoteAction op = new PushConfiguredRemoteAction(
											repository, config, 1000);
									op.execute(monitor);
									PushOperationResult result = op
											.getOperationResult();
									PushResultDialog dlg = new PushResultDialog(
											getShell(), repository, result,
											config.getName());
									dlg.showConfigureButton(false);
									dlg.open();
