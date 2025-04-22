		for (RemoteConfig cfg : allRemotes) {
			if (remoteName != null && cfg.getName().equals(remoteName)) {
				configuredConfig = cfg;
			}
			if (cfg.getName().equals(Constants.DEFAULT_REMOTE_NAME)) {
				defaultConfig = cfg;
			}
