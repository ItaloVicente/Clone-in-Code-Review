			remote = pushRemoteText.getText();
			if (!remote.isEmpty()) {
				myConfig.setString(ConfigConstants.CONFIG_BRANCH_SECTION,
						myBranchName, ConfigConstants.CONFIG_KEY_PUSH_REMOTE,
						remote);
			} else {
				myConfig.unset(ConfigConstants.CONFIG_BRANCH_SECTION,
						myBranchName, ConfigConstants.CONFIG_KEY_PUSH_REMOTE);
			}
