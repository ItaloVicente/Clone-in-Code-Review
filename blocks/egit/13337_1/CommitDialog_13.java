			public void updateChangeIdToggleSelection(boolean selection) {
				changeIdItem.setSelection(selection);
			}
		};

		commitMessageComponent = new CommitMessageComponent(repository,
				listener);
		commitMessageComponent.enableListers(false);
		commitMessageComponent.setDefaults();
		commitMessageComponent.attachControls(commitText, authorText,
				committerText);
		commitMessageComponent.setCommitMessage(commitMessage);
		commitMessageComponent.setAuthor(author);
		commitMessageComponent.setCommitter(committer);
		commitMessageComponent.setAmending(amending);
		commitMessageComponent.setFilesToCommit(getFileList());

		amendingItem.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent arg0) {
				commitMessageComponent.setAmendingButtonSelection(amendingItem
						.getSelection());
