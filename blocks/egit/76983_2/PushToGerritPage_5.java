	private void storeDialogState(String branch) {
		storeRunInBackgroundSelection();
		storeLastUsedUri(uriCombo.getText());
		storeLastUsedBranch(branchText.getText());
		storeLastUsedTopic(topicText.isEnabled(), topicText.getText().trim(),
				branch);
	}

