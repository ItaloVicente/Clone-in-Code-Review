		authorHandler = UIUtils.addPreviousValuesContentProposalToText(authorText, AUTHOR_VALUES_PREF);
		new Label(container, SWT.LEFT).setText(UIText.CommitDialog_Committer);
		committerText = new Text(container, SWT.BORDER);
		committerText.setLayoutData(GridDataFactory.fillDefaults().grab(true, false).create());
		if (committer != null)
			committerText.setText(committer);
		committerText.addModifyListener(new ModifyListener() {
			String oldCommitter = committerText.getText();
			public void modifyText(ModifyEvent e) {
				if (signedOffButton.getSelection()) {
					String newCommitter = committerText.getText();
					String oldSignOff = getSignedOff(oldCommitter);
					String newSignOff = getSignedOff(newCommitter);
					commitText.setText(replaceSignOff(commitText.getText(), oldSignOff, newSignOff));
					oldCommitter = newCommitter;
				}
			}
		});
