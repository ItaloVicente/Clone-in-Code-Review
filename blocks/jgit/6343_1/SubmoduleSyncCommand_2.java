				StoredConfig subConfig;
				String branch;
				try {
					subConfig = subRepo.getConfig();
					branch = getHeadBranch(subRepo);
					String remote = null;
					if (branch != null)
						remote = subConfig.getString(
								ConfigConstants.CONFIG_BRANCH_SECTION
								ConfigConstants.CONFIG_KEY_REMOTE);
					if (remote == null)
						remote = Constants.DEFAULT_REMOTE_NAME;
