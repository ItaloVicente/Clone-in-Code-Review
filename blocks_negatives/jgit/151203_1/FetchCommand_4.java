			boolean result = false;
			StoredConfig config = repo.getConfig();
			result = config.getBoolean(ConfigConstants.CONFIG_FETCH_SECTION,
					null, ConfigConstants.CONFIG_KEY_PRUNE, result);
			result = config.getBoolean(ConfigConstants.CONFIG_REMOTE_SECTION,
					remote, ConfigConstants.CONFIG_KEY_PRUNE, result);
			return result;
