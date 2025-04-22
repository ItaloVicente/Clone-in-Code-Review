					return;

				InputDialog labelDialog = getRefNameInputDialog(
						NLS
								.bind(
										UIText.BranchSelectionDialog_QuestionNewBranchMessage,
										refName, Constants.R_HEADS),
						Constants.R_HEADS);

				if (labelDialog.open() == Window.OK) {
					String newRefName = Constants.R_HEADS + labelDialog.getValue();
					RefUpdate updateRef;
					try {
						updateRef = repo.updateRef(newRefName);
						Ref startRef = repo.getRef(refName);
						ObjectId startAt = repo.resolve(refName);
						String startBranch;
						if (startRef != null)
							startBranch = refName;
						else
							startBranch = startAt.name();
						startBranch = repo.shortenRefName(startBranch);
						updateRef.setNewObjectId(startAt);
						updateRef.setRefLogMessage("branch: Created from " + startBranch, false); //$NON-NLS-1$
						updateRef.update();
						branchTree.refresh();
						markRef(newRefName);
					} catch (Throwable e1) {
						reportError(
								e1,
								UIText.BranchSelectionDialog_ErrorCouldNotCreateNewRef,
								newRefName);
