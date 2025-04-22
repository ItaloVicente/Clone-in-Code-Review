		try (Repository subModRepository = generator.getRepository()) {
			StoredConfig submoduleConfig = subModRepository.getConfig();
			assertEquals(url
					submoduleConfig.getString(
							ConfigConstants.CONFIG_REMOTE_SECTION
							Constants.DEFAULT_REMOTE_NAME
							ConfigConstants.CONFIG_KEY_URL));
		}
