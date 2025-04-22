		boolean canSign = GpgSigner.getDefault() != null;
		signCommitAction.setEnabled(canSign);
		if (!canSign) {
			signCommitAction
					.setToolTipText(UIText.StagingView_Sign_Not_Available);
		}
