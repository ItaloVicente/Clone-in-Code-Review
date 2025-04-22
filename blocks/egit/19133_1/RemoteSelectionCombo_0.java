	public void setSelectedRemote(RemoteConfig remoteConfig) {
		int index = remoteConfigs.indexOf(remoteConfig);
		if (index != -1)
			remoteCombo.select(index);
	}

