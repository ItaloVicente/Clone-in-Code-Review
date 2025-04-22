				dlg.open();
			} else {
				FetchConfiguredRemoteAction op = new FetchConfiguredRemoteAction(
						repository, SimpleConfigureFetchDialog
								.getConfiguredRemote(repository),
						Activator.getDefault().getPreferenceStore().getInt(
								UIPreferences.REMOTE_CONNECTION_TIMEOUT));
				op.start();
			}
		}
