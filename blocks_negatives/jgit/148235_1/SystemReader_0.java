			if (userConfig == null) {
				userConfig = openUserConfig(getSystemConfig(), FS.DETECTED);
			} else {
				getSystemConfig();
			}
			if (userConfig.isOutdated()) {
