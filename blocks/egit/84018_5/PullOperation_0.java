				IWorkspace.AVOID_UPDATE, totalProgress);
	}

	static String getPullTaskName(Repository repo,
			PullReferenceConfig rc) {

		StoredConfig config = repo.getConfig();
		if (rc != null) {
			String remoteUri = config.getString(
					ConfigConstants.CONFIG_REMOTE_SECTION, rc.remote,
					ConfigConstants.CONFIG_KEY_URL);
			return "Pulling " + rc.remote + " from " + remoteUri; //$NON-NLS-1$ //$NON-NLS-2$
		}

		String branchName;
		try {
			String fullBranch = repo.getFullBranch();
			branchName = fullBranch != null
					? fullBranch.substring(Constants.R_HEADS.length())
					: ""; //$NON-NLS-1$
		} catch (IOException e) {
			return "Pulling from " + repo.toString(); //$NON-NLS-1$
		}

		String remote = config.getString(ConfigConstants.CONFIG_BRANCH_SECTION,
				branchName, ConfigConstants.CONFIG_KEY_REMOTE);
		if (remote == null) {
			remote = Constants.DEFAULT_REMOTE_NAME;
		}

		String remoteUri = config.getString(
				ConfigConstants.CONFIG_REMOTE_SECTION, remote,
				ConfigConstants.CONFIG_KEY_URL);
		if (remoteUri != null) {
			return "Pulling " + remote + " from " + remoteUri; //$NON-NLS-1$ //$NON-NLS-2$
		}
		return "Pulling from " + repo.getDirectory(); //$NON-NLS-1$
