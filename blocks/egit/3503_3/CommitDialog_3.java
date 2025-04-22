			public void updateChangeIdToggleSelection(boolean selection) {
				changeIdItem.setSelection(selection);
				createChangeId = selection;
			}
		};

		commitMessageComponent = new CommitMessageComponent(repository, listener);
		commitMessageComponent.attachControls(commitText, authorText, committerText);
		commitMessageComponent.setPreviousCommitMessage(previousCommitMessage);
		commitMessageComponent.setCommitMessage(commitMessage);
		commitMessageComponent.setPreviousAuthor(previousAuthor);
		commitMessageComponent.setAuthor(author);
		commitMessageComponent.setCommitter(committer);
		commitMessageComponent.setAmending(amending);
		commitMessageComponent.setFilesToCommit(getFileList());

		amendingItem.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent arg0) {
				commitMessageComponent.setAmendingButtonSelection(amendingItem.getSelection());
			}
