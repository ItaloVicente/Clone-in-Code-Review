	private static boolean getRenames(Config config) {
		RenameDetectionType renameDetectionType = DiffConfig.parseRenameDetectionType(config.getString(
				ConfigConstants.CONFIG_MERGE_SECTION
		return !renameDetectionType.equals(RenameDetectionType.FALSE);
	}

