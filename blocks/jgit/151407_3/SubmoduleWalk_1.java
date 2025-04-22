		IgnoreSubmoduleMode mode = repoConfig.getEnum(
				IgnoreSubmoduleMode.values()
				ConfigConstants.CONFIG_SUBMODULE_SECTION
				ConfigConstants.CONFIG_KEY_IGNORE
		if (mode != null) {
			return mode;
		}
