		String remote = remoteName;
		if (remote == null) {
			remote = repoConfig.getString(
					ConfigConstants.CONFIG_BRANCH_SECTION
					ConfigConstants.CONFIG_KEY_REMOTE);
			if (remote == null)
				remote = Constants.DEFAULT_REMOTE_NAME;
		}
