	private void findRemoteConfig() throws URISyntaxException {
		List<RemoteConfig> allRemoteConfigs = RemoteConfig.getAllRemoteConfigs(config);
		for (RemoteConfig rc : allRemoteConfigs) {
			if (rc.getName().equals(remoteName))
				remoteConfig = rc;
		}
	}

