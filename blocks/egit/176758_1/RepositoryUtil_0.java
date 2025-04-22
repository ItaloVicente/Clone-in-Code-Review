	public static String getDefaultBranchName()
			throws ConfigInvalidException, IOException {
		return SystemReader.getInstance().getUserConfig().getString(
				ConfigConstants.CONFIG_INIT_SECTION, null,
				ConfigConstants.CONFIG_KEY_DEFAULT_BRANCH);
	}

