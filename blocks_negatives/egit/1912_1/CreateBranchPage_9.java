		return Constants.R_HEADS + nameText.getText();
	}

	private String getSourceBranchName() {
		if (commitMode)
			return myBaseCommit.name();
		if (this.branchCombo != null)
			return this.branchCombo.getText();
		else if (myBaseBranch != null)
			return myBaseBranch.getName();
		else
			return null;
