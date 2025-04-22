		if (configuredRemotes != null)
			removeUnusableRemoteConfigs(configuredRemotes);
		if (configuredRemotes == null || configuredRemotes.isEmpty())
			this.configuredRemotes = null;
		else {
			this.configuredRemotes = configuredRemotes;
			this.remoteConfig = selectDefaultRemoteConfig();
		}
