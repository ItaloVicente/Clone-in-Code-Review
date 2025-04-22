									new FetchConfiguredRemoteAction(
											repository,
											config,
											Activator
													.getDefault()
													.getPreferenceStore()
													.getInt(
															UIPreferences.REMOTE_CONNECTION_TIMEOUT))
											.start();
