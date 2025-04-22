				if (remoteCombo.getSelectionIndex() < remoteConfigs.size()) {
					RemoteConfig remoteConfig = getSelectedRemote();
					remoteSelected(remoteConfig);
				} else {
					showNewRemoteDialog();
				}
