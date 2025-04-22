	public boolean isBare() {
		boolean bareSet = getConfig().getString(
				ConfigConstants.CONFIG_CORE_SECTION
				ConfigConstants.CONFIG_KEY_BARE) != null;
		if (bareSet)
			return getConfig().getBoolean(ConfigConstants.CONFIG_CORE_SECTION
					ConfigConstants.CONFIG_KEY_BARE

		if (gitDirExplicit && workDir == null)
			return true;

		if (getDirectory().getName().equals(Constants.DOT_GIT))
			return false;

		return true;
	}

