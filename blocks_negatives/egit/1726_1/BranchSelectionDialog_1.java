						RefRename renameRef = repo.renameRef(refName,
								newRefName);
						if (renameRef.rename() != Result.RENAMED) {
							reportError(
									null,
									UIText.BranchSelectionDialog_ErrorCouldNotRenameRef,
									refName, newRefName, renameRef.getResult());
						}
