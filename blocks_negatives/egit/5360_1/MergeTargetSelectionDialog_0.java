	/**
	 * Get the target merge ref name for the currently checkout branch
	 *
	 * @param repo
	 * @return ref node
	 */
	private static String getMergeTarget(Repository repo) {
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

			return merge;
		else
					+ Repository.shortenRefName(merge);
	}

	/**
	 * Get the target merge ref name for the currently checkout branch
	 *
	 * @param repo
	 * @return ref node
	 */
	private static int getSelectSetting(Repository repo) {
		return getMergeTarget(repo) != null ? SELECT_CURRENT_REF : 0;
	}

