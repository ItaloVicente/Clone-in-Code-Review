						.getenv(Constants.GIT_CONFIG_NOSYSTEM_KEY)))) {
			list(SystemReader.getInstance().getSystemConfig());
		}
		if (global || isListAll()) {
			list(SystemReader.getInstance().getUserConfig());
		}
		if (local || isListAll()) {
