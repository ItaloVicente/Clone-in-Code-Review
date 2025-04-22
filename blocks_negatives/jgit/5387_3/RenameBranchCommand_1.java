					String oldMerge = repoConfig.getString(
							ConfigConstants.CONFIG_BRANCH_SECTION,
							shortOldName, ConfigConstants.CONFIG_KEY_MERGE);
					if (oldMerge != null) {
						repoConfig.setString(
								ConfigConstants.CONFIG_BRANCH_SECTION, newName,
								ConfigConstants.CONFIG_KEY_MERGE, oldMerge);
					}
					repoConfig
							.unsetSection(
									ConfigConstants.CONFIG_BRANCH_SECTION,
									shortOldName);
					repoConfig.save();
				}
