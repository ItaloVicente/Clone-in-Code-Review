		keyFormat = config.getEnum(GpgFormat.values()
				ConfigConstants.CONFIG_GPG_SECTION
				ConfigConstants.CONFIG_KEY_FORMAT
		signingKey = config.getString(ConfigConstants.CONFIG_USER_SECTION
				ConfigConstants.CONFIG_KEY_SIGNINGKEY);

		String exe = config.getString(ConfigConstants.CONFIG_GPG_SECTION
				keyFormat.toConfigValue()
		if (exe == null) {
			exe = config.getString(ConfigConstants.CONFIG_GPG_SECTION
					ConfigConstants.CONFIG_KEY_PROGRAM);
		}
		program = exe;
		signCommits = config.getBoolean(ConfigConstants.CONFIG_COMMIT_SECTION
				ConfigConstants.CONFIG_KEY_GPGSIGN
		signAllTags = config.getBoolean(ConfigConstants.CONFIG_TAG_SECTION
				ConfigConstants.CONFIG_KEY_GPGSIGN
		forceAnnotated = config.getBoolean(ConfigConstants.CONFIG_TAG_SECTION
				ConfigConstants.CONFIG_KEY_FORCE_SIGN_ANNOTATED
