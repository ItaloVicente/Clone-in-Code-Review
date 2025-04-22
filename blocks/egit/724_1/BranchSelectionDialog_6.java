						branchTree.refresh();
						markRef(newRefName);
					} catch (Throwable e1) {
						reportError(
								e1,
								UIText.BranchSelectionDialog_ErrorCouldNotRenameRef,
								refName, newRefName, e1.getMessage());
