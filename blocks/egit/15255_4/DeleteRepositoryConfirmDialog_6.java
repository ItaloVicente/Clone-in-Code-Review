
	private static Text createIndentedLabel(Composite main, String text) {
		Text widget = UIUtils.createSelectableLabel(main, 0);
		widget.setText(text);
		int indent = 20;
		GridDataFactory.fillDefaults().grab(true, false).indent(indent, 0)
				.applyTo(widget);
		return widget;
	}

	private void updateUI() {
		getButton(IDialogConstants.OK_ID).setEnabled(shouldDeleteGitDir);
		deleteWorkDir.setEnabled(shouldDeleteGitDir);
		deleteWorkDirLabel.setEnabled(shouldDeleteGitDir);
		removeProjects
				.setEnabled(shouldDeleteGitDir && !shouldDeleteWorkingDir);
		if (shouldDeleteWorkingDir && numberOfProjects > 0) {
			removeProjects.setSelection(true);
			shouldRemoveProjects = true;
		}
		if (shouldDeleteGitDir)
			setMessage(
					UIText.DeleteRepositoryConfirmDialog_DeleteRepositoryNoUndoWarning,
					IMessageProvider.WARNING);
		else
			setMessage(UIText.DeleteRepositoryConfirmDialog_DeleteRepositoryConfirmMessage);
	}
