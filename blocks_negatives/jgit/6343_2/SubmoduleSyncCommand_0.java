				StoredConfig subConfig = subRepo.getConfig();
				String branch = getHeadBranch(subRepo);
				String remote = null;
				if (branch != null)
					remote = subConfig.getString(
							ConfigConstants.CONFIG_BRANCH_SECTION, branch,
							ConfigConstants.CONFIG_KEY_REMOTE);
				if (remote == null)
					remote = Constants.DEFAULT_REMOTE_NAME;
