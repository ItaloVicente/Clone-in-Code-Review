		remote = myConfig.getString(ConfigConstants.CONFIG_BRANCH_SECTION,
				myBranchName, ConfigConstants.CONFIG_KEY_PUSH_REMOTE);
		if (remote == null)
			remote = ""; //$NON-NLS-1$
		pushRemoteText.setText(remote);

