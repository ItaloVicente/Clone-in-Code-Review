		String noSystemConfigValue = SystemReader.getInstance()
				.getenv(Constants.GIT_CONFIG_NOSYSTEM_KEY);
		if (StringUtils.isEmptyOrNull(noSystemConfigValue) ||
				options.getIgnoreSystemGitConfig()) {
			systemConfig = SystemReader.getInstance()
					.openSystemConfig(null
		}
		else {
			systemConfig = noopConfig();
		}

		if(options.getIgnoreGlobalGitConfig()){
			userConfig = noopConfig();
		} else {
			userConfig = SystemReader.getInstance()
					.openUserConfig(systemConfig
		}

