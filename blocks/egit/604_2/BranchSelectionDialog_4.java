						refPrefix = Constants.R_HEADS;
					else if (refName.startsWith(Constants.R_REMOTES))
						refPrefix = Constants.R_REMOTES;
					else if (refName.startsWith(Constants.R_TAGS))
						refPrefix = Constants.R_TAGS;
					else {
						return;
					}

					String branchName = refName.substring(refPrefix.length());

