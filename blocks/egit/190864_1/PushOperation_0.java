	public static RemoteConfig getRemote(String branch,
			Config config) {
		if (branch == null) {
			return null;
		}
		String remoteName = null;
		if (!ObjectId.isId(branch)) {
			remoteName = config.getString(ConfigConstants.CONFIG_BRANCH_SECTION,
					branch, ConfigConstants.CONFIG_REMOTE_SECTION);
		}
		List<RemoteConfig> allRemotes;
		try {
			allRemotes = RemoteConfig.getAllRemoteConfigs(config);
		} catch (URISyntaxException e) {
			allRemotes = new ArrayList<>();
		}

		RemoteConfig configuredConfig = null;
		RemoteConfig defaultConfig = null;
		for (RemoteConfig cfg : allRemotes) {
			if (remoteName != null && cfg.getName().equals(remoteName)) {
				configuredConfig = cfg;
			}

			if (cfg.getName().equals(Constants.DEFAULT_REMOTE_NAME)) {
				defaultConfig = cfg;
			}
		}

		if (configuredConfig != null) {
			return configuredConfig;
		}
		if (defaultConfig != null
				&& !defaultConfig.getPushRefSpecs().isEmpty()) {
			return defaultConfig;
		}
		return null;
	}

