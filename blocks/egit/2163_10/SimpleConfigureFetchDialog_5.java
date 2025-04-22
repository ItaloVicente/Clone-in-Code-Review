								int timeout = Activator
										.getDefault()
										.getPreferenceStore()
										.getInt(
												UIPreferences.REMOTE_CONNECTION_TIMEOUT);
								FetchOperationUI op = new FetchOperationUI(
										repository, config, timeout, true);
