		final ICommitMessageComponentNotifications listener = new ICommitMessageComponentNotifications() {

			public void updateSignedOffToggleSelection(boolean selection) {
				signedOffByButton.setSelection(selection);
			}

			public void updateChangeIdToggleSelection(boolean selection) {
				addChangeIdButton.setSelection(selection);
			}
		};
		commitMessageComponent = new CommitMessageComponent(listener);
		commitMessageComponent.attachControls(commitMessageText, authorText, committerText);
		addChangeIdButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				commitMessageComponent.setChangeIdButtonSelection(addChangeIdButton.getSelection());
			}
		});
		signedOffByButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				commitMessageComponent.setSignedOffButtonSelection(signedOffByButton.getSelection());
			}
		});
		amendPreviousCommitButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				commitMessageComponent.setAmendingButtonSelection(amendPreviousCommitButton.getSelection());
			}
		});


