		signCommitItem = new ToolItem(messageToolbar, SWT.CHECK);

		signCommitItem.setToolTipText(UIText.CommitDialog_SignCommit);
		Image signCommitImage = UIIcons.SIGN_COMMIT.createImage();
		UIUtils.hookDisposal(signCommitItem, signCommitImage);
		signCommitItem.setImage(signCommitImage);

