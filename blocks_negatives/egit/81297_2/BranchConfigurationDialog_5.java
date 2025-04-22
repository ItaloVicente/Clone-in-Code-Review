				String merge = branchText.getText();
				if (merge.length() > 0)
					myConfig.setString(ConfigConstants.CONFIG_BRANCH_SECTION,
							myBranchName, ConfigConstants.CONFIG_KEY_MERGE,
							merge);
				else
					myConfig.unset(ConfigConstants.CONFIG_BRANCH_SECTION,
							myBranchName, ConfigConstants.CONFIG_KEY_MERGE);

				String remote = remoteText.getText();
				if (remote.length() > 0)
					myConfig.setString(ConfigConstants.CONFIG_BRANCH_SECTION,
							myBranchName, ConfigConstants.CONFIG_KEY_REMOTE,
							remote);
				else
					myConfig.unset(ConfigConstants.CONFIG_BRANCH_SECTION,
							myBranchName, ConfigConstants.CONFIG_KEY_REMOTE);

				boolean rebaseFlag = rebase.getSelection();
				if (rebaseFlag)
					myConfig.setBoolean(ConfigConstants.CONFIG_BRANCH_SECTION,
							myBranchName, ConfigConstants.CONFIG_KEY_REBASE,
							true);
				else
					myConfig.unset(ConfigConstants.CONFIG_BRANCH_SECTION,
							myBranchName, ConfigConstants.CONFIG_KEY_REBASE);
