		RepositorySaveableFilter filter = new RepositorySaveableFilter(
				repository);
		boolean success = workbench.saveAll(window, window, filter, true);
		if (success && cancelConfirmationQuestion != null && filter.isAnythingSaved()){
			String[] buttons = new String[] { IDialogConstants.YES_LABEL,
					IDialogConstants.NO_LABEL };
			MessageDialog dialog = new MessageDialog(window.getShell(),
					UIText.CancelAfterSaveDialog_Title, null,
					cancelConfirmationQuestion,
					MessageDialog.QUESTION, buttons, 0) {
				protected int getShellStyle() {
					return (SWT.TITLE | SWT.BORDER | SWT.APPLICATION_MODAL
							| SWT.SHEET | getDefaultOrientation());
				}
			};
			int choice = dialog.open();
			if (choice != 1) // user clicked "yes" or closed dialog -> cancel
				return false;
		}
		return success;
