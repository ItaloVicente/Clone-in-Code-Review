
	private void reloadTree(String refName) {
		try {
			branchTree.removeAll();
			fillTreeWithBranches(refName);
		} catch (Throwable e1) {
			reportError(e1,
					UIText.BranchSelectionDialog_BranchSelectionDialog_CreateBranchFailedTitle,
					UIText.BranchSelectionDialog_ErrorCouldNotRefreshBranchList);
		}
	}
