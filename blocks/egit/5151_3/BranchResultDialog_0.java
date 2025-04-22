
	protected void buttonPressed(int buttonId) {
		switch (buttonId) {
		case IDialogConstants.PROCEED_ID:
			CommonUtils.runCommand(CommitCommand.ID, new StructuredSelection(
					new RepositoryNode(null, repository)));
			break;
		case IDialogConstants.ABORT_ID:
			CommonUtils.runCommand(ResetCommand.ID, new StructuredSelection(
					new RepositoryNode(null, repository)));
			break;
		}
		super.buttonPressed(buttonId);
	}

	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		super.createButtonsForButtonBar(parent);
		createButton(parent, IDialogConstants.ABORT_ID,
				UIText.BranchResultDialog_buttonReset, false);
		createButton(parent, IDialogConstants.PROCEED_ID,
				UIText.BranchResultDialog_buttonCommit, false);
	}
