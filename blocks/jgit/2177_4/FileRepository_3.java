		if (systemConfig.isOutdated()) {
			try {
				loadSystemConfig();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
