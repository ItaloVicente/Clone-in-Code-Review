								int timeout = Activator
										.getDefault()
										.getPreferenceStore()
										.getInt(
												UIPreferences.REMOTE_CONNECTION_TIMEOUT);
								PushOperationUI op = new PushOperationUI(
										repository, config, timeout, true);
								try {
									PushOperationResult result = op
											.execute(monitor);
									PushResultDialog dlg = new PushResultDialog(
											getShell(), repository, result, op
													.getDestinationString());
									dlg.open();
								} catch (CoreException e) {
									Activator.handleError(e.getMessage(), e,
											true);
								}
