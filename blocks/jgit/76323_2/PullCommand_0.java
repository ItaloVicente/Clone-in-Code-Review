
	private FastForwardMode getFastForwardMode() {
		Config config = repo.getConfig();
		Merge ffMode = config.getEnum(Merge.values()
				ConfigConstants.CONFIG_PULL_SECTION
				ConfigConstants.CONFIG_KEY_FF
		if (ffMode == null) {
			ffMode = config.getEnum(Merge.values()
					ConfigConstants.CONFIG_MERGE_SECTION
					ConfigConstants.CONFIG_KEY_FF
		}
		return ffMode != null ? FastForwardMode.valueOf(ffMode) : null;
	}
