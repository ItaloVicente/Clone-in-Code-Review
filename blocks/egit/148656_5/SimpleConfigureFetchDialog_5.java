		for (RemoteConfig cfg : allRemotes) {
			if (cfg.getName().equals(Constants.DEFAULT_REMOTE_NAME)) {
				defaultConfig = cfg;
			}
			if (remoteName != null && cfg.getName().equals(remoteName)) {
				configuredConfig = cfg;
			}
