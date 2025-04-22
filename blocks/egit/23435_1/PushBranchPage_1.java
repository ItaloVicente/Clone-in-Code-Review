		StoredConfig config = repository.getConfig();
		String branchName = Repository.shortenRefName(ref.getName());

		String merge = config.getString(ConfigConstants.CONFIG_BRANCH_SECTION,
				branchName, ConfigConstants.CONFIG_KEY_MERGE);
		if (merge != null && merge.startsWith(Constants.R_HEADS))
			return Repository.shortenRefName(merge);

		return branchName;
