		remoteSelectionCombo
				.addRemoteSelectionListener(new RemoteSelectionCombo.IRemoteSelectionListener() {
					@Override
					public void remoteSelected(RemoteConfig remoteConfig) {
						selectedRemoteConfig = remoteConfig;
					}
				});
