	public boolean isRemoteLocal() {
		return LOCAL_REPOSITORY.equals(getRemote());
	}

	public String getRemote() {
		return config.getString(ConfigConstants.CONFIG_BRANCH_SECTION
				branchName
	}

	public String getMerge() {
		return config.getString(
				ConfigConstants.CONFIG_BRANCH_SECTION
				ConfigConstants.CONFIG_KEY_MERGE);
	}

