				if (refName.startsWith(Constants.R_HEADS))
					refPrefix = Constants.R_HEADS;
				else if (refName.startsWith(Constants.R_REMOTES))
					refPrefix = Constants.R_REMOTES;
				else if (refName.startsWith(Constants.R_TAGS))
					refPrefix = Constants.R_TAGS;
				else {
					return;
				}

				String branchName = refName.substring(refPrefix.length());

				InputDialog labelDialog = getRefNameInputDialog(
						NLS.bind(
								UIText.BranchSelectionAndEditDialog_QuestionNewBranchNameMessage,
								branchName, refPrefix), refPrefix, branchName);
				if (labelDialog.open() == Window.OK) {
					String newRefName = refPrefix + labelDialog.getValue();
					try {
						new Git(repo).branchRename().setOldName(refName)
								.setNewName(labelDialog.getValue()).call();
						branchTree.refresh();
						markRef(newRefName);
					} catch (Throwable e1) {
						reportError(
								e1,
								UIText.BranchSelectionAndEditDialog_ErrorCouldNotRenameRef,
								refName, newRefName, e1.getMessage());
					}
