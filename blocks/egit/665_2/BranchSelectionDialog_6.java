
	private boolean isBranchSelected() {
		String refName = refNameFromDialog();


		boolean branchSelected = refName != null
				&& (refName.startsWith(Constants.R_HEADS));
		return branchSelected;
	}
