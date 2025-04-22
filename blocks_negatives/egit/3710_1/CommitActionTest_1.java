	void configureTestCommitterAsUser(Repository repository) {
		StoredConfig config = repository.getConfig();
		config.setString(ConfigConstants.CONFIG_USER_SECTION, null,
				ConfigConstants.CONFIG_KEY_NAME, TestUtil.TESTCOMMITTER_NAME);
		config.setString(ConfigConstants.CONFIG_USER_SECTION, null,
				ConfigConstants.CONFIG_KEY_EMAIL, TestUtil.TESTCOMMITTER_EMAIL);
	}

