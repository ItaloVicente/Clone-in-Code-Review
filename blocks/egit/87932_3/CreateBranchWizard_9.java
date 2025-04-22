
	public void setNewBranchName(String newBranchName) {
		this.newBranchName = newBranchName;
		if (myPage != null) {
			myPage.setBranchName(newBranchName);
		}
	}
