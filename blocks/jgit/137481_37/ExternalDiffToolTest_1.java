		FileBasedConfig config = db.getConfig();
		String customToolname = "customTool";
		config.setString(ConfigConstants.CONFIG_DIFFTOOL_SECTION
				customToolname
				"cmd"
		config.setString(ConfigConstants.CONFIG_DIFF_SECTION
				ConfigConstants.CONFIG_KEY_PATH
		config.setString(ConfigConstants.CONFIG_DIFF_SECTION
				ConfigConstants.CONFIG_KEY_PROMPT
		config.setString(ConfigConstants.CONFIG_DIFF_SECTION
				ConfigConstants.CONFIG_KEY_GUITOOL
		config.setString(ConfigConstants.CONFIG_DIFF_SECTION
				ConfigConstants.CONFIG_KEY_TRUST_EXIT_CODE
				"--no-trust-exit-code");
