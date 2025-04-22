				String branchName = refName.substring(refPrefix.length());

				InputDialog labelDialog = getRefNameInputDialog(NLS
						.bind(
								UIText.BranchSelectionDialog_QuestionNewBranchNameMessage,
								branchName, refPrefix), refPrefix);
				if (labelDialog.open() == Window.OK) {
					String newRefName = refPrefix + labelDialog.getValue();
					try {
						RefRename renameRef = repo.renameRef(refName, newRefName);
						if (renameRef.rename() != Result.RENAMED) {
