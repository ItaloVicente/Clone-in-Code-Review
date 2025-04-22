					refNameFromDialog();

					String branchName;
					if (refName.startsWith(Constants.R_HEADS))
						branchName = refName.substring(Constants.R_HEADS.length());
					else
						branchName = refName;
					InputDialog labelDialog = getRefNameInputDialog(NLS
							.bind(
									UIText.BranchSelectionDialog_QuestionNewBranchNameMessage,
									branchName));
					if (labelDialog.open() == Window.OK) {
						String newRefName = Constants.R_HEADS + labelDialog.getValue();
						try {
							RefRename renameRef = repo.renameRef(refName, newRefName);
							if (renameRef.rename() != Result.RENAMED) {
								reportError(
										null,
										UIText.BranchSelectionDialog_BranchSelectionDialog_RenamedFailedTitle,
										UIText.BranchSelectionDialog_ErrorCouldNotRenameRef,
										refName, newRefName, renameRef
												.getResult());
							}
						} catch (Throwable e1) {
							reportError(
									e1,
									UIText.BranchSelectionDialog_BranchSelectionDialog_RenamedFailedTitle,
									UIText.BranchSelectionDialog_ErrorCouldNotRenameRef,
									refName, newRefName, e1.getMessage());
						}
						try {
							branchTree.removeAll();
							fillTreeWithBranches(newRefName);
						} catch (Throwable e1) {
							reportError(
									e1,
									UIText.BranchSelectionDialog_BranchSelectionDialog_RenamedFailedTitle,
									UIText.BranchSelectionDialog_ErrorCouldNotRefreshBranchList);
						}
					}
