	String getFullRemoteReference() {
		if (!remoteBranchNameText.getText().startsWith(Constants.R_REFS)) {
			return Constants.R_HEADS + remoteBranchNameText.getText();
		} else {
			return remoteBranchNameText.getText();
		}
