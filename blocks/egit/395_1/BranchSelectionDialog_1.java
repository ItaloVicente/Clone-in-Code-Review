						reloadTree(newRefName);
					}
				}

				public void widgetDefaultSelected(SelectionEvent e) {
					widgetSelected(e);
				}
			});
			newTagButton.addSelectionListener(new SelectionListener() {

				public void widgetSelected(SelectionEvent e) {
					refNameFromDialog();

					InputDialog labelDialog = getRefNameInputDialog(UIText.BranchSelectionDialog_QuestionNewTagMessage);
					if (labelDialog.open() == Window.OK) {
						String newRefName = Constants.R_TAGS + labelDialog.getValue();
