	private AddRemotePage getAddRemotePage() {
		if (addRemotePage != null)
			return addRemotePage;
		else
			return pushBranchPage.getAddRemotePage();
	}

