	public static boolean canFetchFromGerrit(@NonNull Repository repository) {
		Config config = repository.getConfig();
		try {
			List<RemoteConfig> remoteConfigs = RemoteConfig
					.getAllRemoteConfigs(config);
			for (RemoteConfig remoteConfig : remoteConfigs) {
				if (GerritUtil.isGerritFetch(remoteConfig)) {
					return true;
				}
			}
		} catch (URISyntaxException e) {
			return false;
		}
		return false;
	}

	public static boolean canPushToGerrit(@NonNull Repository repository) {
		Config config = repository.getConfig();
		try {
			List<RemoteConfig> remoteConfigs = RemoteConfig
					.getAllRemoteConfigs(config);
			for (RemoteConfig remoteConfig : remoteConfigs) {
				if (GerritUtil.isGerritPush(remoteConfig)) {
					return true;
				}
			}
		} catch (URISyntaxException e) {
			return false;
		}
		return false;
	}

