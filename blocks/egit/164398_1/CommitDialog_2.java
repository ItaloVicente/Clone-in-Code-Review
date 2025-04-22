		boolean canSign = GpgSigner.getDefault() != null;
		signCommitItem.setEnabled(canSign);
		if (!canSign) {
			signCommitItem
					.setToolTipText(UIText.CommitDialog_Sign_Not_Available);
		}
