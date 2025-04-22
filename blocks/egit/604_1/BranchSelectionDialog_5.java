					String refName = refNameFromDialog();

					String refPrefix;

					if (refName.equals(Constants.HEAD))
						return;

					if (refName.startsWith(Constants.R_HEADS))
						refPrefix = Constants.R_HEADS;
					else if (refName.startsWith(Constants.R_REMOTES))
						refPrefix = Constants.R_REMOTES;
					else {
						return;
					}

					InputDialog labelDialog = getRefNameInputDialog(
							NLS
									.bind(
											UIText.BranchSelectionDialog_QuestionNewBranchMessage,
											refName, refPrefix), refPrefix);
