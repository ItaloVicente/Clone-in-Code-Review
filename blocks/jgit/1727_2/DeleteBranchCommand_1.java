					result.add(fullName);
					if (fullName.startsWith(Constants.R_HEADS)) {
						String shortenedName = fullName
								.substring(Constants.R_HEADS.length());
						repo.getConfig().unsetSection(
								ConfigConstants.CONFIG_BRANCH_SECTION
								shortenedName);
						repo.getConfig().save();
					}
