		GlobalConfigCache configs = GlobalConfigCache.getInstance();
		try {
			userConfig = configs.getUserConfig();
		} catch (ConfigInvalidException e) {
			throw new IOException(e.getMessage()
		}
