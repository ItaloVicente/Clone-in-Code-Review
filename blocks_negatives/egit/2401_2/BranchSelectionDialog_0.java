				Ref ref = refFromDialog();

				InputDialog labelDialog = getRefNameInputDialog(NLS.bind(
						UIText.BranchSelectionDialog_QuestionNewBranchMessage,
						ref.getName(), Constants.R_HEADS), Constants.R_HEADS,
						null);

				if (labelDialog.open() == Window.OK) {
					String newRefName = labelDialog.getValue();
					CreateLocalBranchOperation cbop = new CreateLocalBranchOperation(
							repo, newRefName, ref);
