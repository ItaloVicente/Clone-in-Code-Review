		Config config = repo.getConfig();
		String configuredUrl = config.getString(BUGTRACK_SECTION, null,
				BUGTRACK_URL);
		if (configuredUrl != null) {
			return configuredUrl;
		}
		return config.getString(ConfigConstants.CONFIG_REMOTE_SECTION,
