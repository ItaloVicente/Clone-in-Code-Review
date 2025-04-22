	@Override
	protected void okPressed() {
		commitMessage = messageArea.getCommitMessage();
		useChangeId = addChangeIdAction.isChecked();
		super.okPressed();
	}

