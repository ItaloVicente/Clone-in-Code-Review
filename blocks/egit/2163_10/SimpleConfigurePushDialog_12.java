									int timeout = Activator
											.getDefault()
											.getPreferenceStore()
											.getInt(
													UIPreferences.REMOTE_CONNECTION_TIMEOUT);
									PushOperationUI op = new PushOperationUI(
											repository, config, timeout, false);
									op.start();
