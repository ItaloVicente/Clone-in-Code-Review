	public boolean isBare() {
		boolean bare = getConfig().getBoolean(
				ConfigConstants.CONFIG_CORE_SECTION
				ConfigConstants.CONFIG_KEY_BARE
		if (bare)
			return true;
		String workTree = getConfig().getString(
				ConfigConstants.CONFIG_CORE_SECTION
		if (workTree != null)
			return false;

		if (getDirectory().getName().equals(Constants.DOT_GIT))
			return false;

		return true;
	}

