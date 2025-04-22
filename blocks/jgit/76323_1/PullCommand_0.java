
	private static FastForwardMode getFastForwardMode(Config config) {
		Merge ffMode = config.getEnum(Merge.values()
				ConfigConstants.CONFIG_PULL_SECTION
				ConfigConstants.CONFIG_KEY_FF
		return ffMode != null ? FastForwardMode.valueOf(ffMode) : null;
	}
