	public void setBranchName(String branchName) {
		this.branchName = branchName;
		if (nameText != null) {
			nameText.setText(branchName);
		}
	}

