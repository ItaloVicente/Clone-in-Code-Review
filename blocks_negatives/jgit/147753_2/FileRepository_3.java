		if (systemConfig.isOutdated()) {
			try {
				loadSystemConfig();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		if (userConfig.isOutdated()) {
			try {
				loadUserConfig();
			} catch (IOException e) {
				throw new RuntimeException(e);
