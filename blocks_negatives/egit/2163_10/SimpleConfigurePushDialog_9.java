								PushConfiguredRemoteAction op = new PushConfiguredRemoteAction(
										repository,
										config,
										Activator
												.getDefault()
												.getPreferenceStore()
												.getInt(
														UIPreferences.REMOTE_CONNECTION_TIMEOUT));
								op.setDryRun(true);
								op.execute(monitor);
								PushOperationResult result = op
										.getOperationResult();
								PushResultDialog dlg = new PushResultDialog(
										getShell(), repository, result, config
												.getName());
								dlg.showConfigureButton(false);
								dlg.open();

