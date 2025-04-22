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
