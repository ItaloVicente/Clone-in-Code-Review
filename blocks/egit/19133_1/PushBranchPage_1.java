	private void setRemoteConfigs() {
		remoteSelectionCombo.setItems(remoteConfigs);

		String branchName = Repository.shortenRefName(ref.getName());
		String remoteName = repository.getConfig().getString(
				ConfigConstants.CONFIG_BRANCH_SECTION, branchName,
				ConfigConstants.CONFIG_KEY_REMOTE);
		if (remoteName != null) {
			for (RemoteConfig rc : remoteConfigs) {
				if (remoteName.equals(rc.getName()))
					remoteSelectionCombo.setSelectedRemote(rc);
			}
		}

		remoteConfig = remoteSelectionCombo.getSelectedRemote();
	}

