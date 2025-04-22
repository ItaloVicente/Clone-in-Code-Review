
	private boolean isBranchSelected() {
		String refName = refNameFromDialog();


		boolean branchSelected = refName != null
				&& (refName.startsWith(Constants.R_HEADS) || refName
						.startsWith(Constants.R_REMOTES));
		return branchSelected;
	}
