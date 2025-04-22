	protected static String getMergeTarget(Repository repo) {
		String branch;
		try {
			branch = repo.getBranch();
		} catch (IOException e) {
			return null;
		}
		if (branch == null)
			return null;

		String merge = repo.getConfig().getString(
				ConfigConstants.CONFIG_BRANCH_SECTION, branch,
				ConfigConstants.CONFIG_KEY_MERGE);
		if (merge == null)
			return null;

		String remote = repo.getConfig().getString(
				ConfigConstants.CONFIG_BRANCH_SECTION, branch,
				ConfigConstants.CONFIG_KEY_REMOTE);
		if (remote == null)
			return null;

		if (".".equals(remote)) //$NON-NLS-1$
			return merge;
		else
			return Constants.R_REMOTES + remote + "/" //$NON-NLS-1$
					+ Repository.shortenRefName(merge);
	}

	protected static int getSelectSetting(Repository repo) {
		return getMergeTarget(repo) != null ? SELECT_CURRENT_REF : 0;
	}

