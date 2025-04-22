		committerText.addModifyListener(new ModifyListener() {
			String oldCommitter = committerText.getText();

			public void modifyText(ModifyEvent e) {
				if (signedOffItem.getSelection()) {
					String newCommitter = committerText.getText();
					String oldSignOff = getSignedOff(oldCommitter);
					String newSignOff = getSignedOff(newCommitter);
					commitText.setText(replaceSignOff(commitText.getText(),
							oldSignOff, newSignOff));
					oldCommitter = newCommitter;
				}
			}
		});

		committerHandler = UIUtils.addPreviousValuesContentProposalToText(
				committerText, COMMITTER_VALUES_PREF);
