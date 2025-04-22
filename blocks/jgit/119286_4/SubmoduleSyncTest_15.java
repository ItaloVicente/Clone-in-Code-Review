		try (Repository subModRepository1 = generator.getRepository()) {
			StoredConfig submoduleConfig = subModRepository1.getConfig();
					submoduleConfig.getString(
							ConfigConstants.CONFIG_REMOTE_SECTION
							Constants.DEFAULT_REMOTE_NAME
							ConfigConstants.CONFIG_KEY_URL));
		}
