		if (removeDeletedRefs != null)
			return removeDeletedRefs.booleanValue();
			boolean result = false;
			StoredConfig config = repo.getConfig();
			result = config.getBoolean(ConfigConstants.CONFIG_FETCH_SECTION
					null
			result = config.getBoolean(ConfigConstants.CONFIG_REMOTE_SECTION
					remote
			return result;
		}
