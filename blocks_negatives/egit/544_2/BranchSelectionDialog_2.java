						try {
							branchTree.removeAll();
							fillTreeWithBranches(newRefName);
						} catch (Throwable e1) {
							reportError(e1,
									UIText.BranchSelectionDialog_BranchSelectionDialog_CreateFailedTitle,
									UIText.BranchSelectionDialog_ErrorCouldNotRefreshBranchList);
						}
