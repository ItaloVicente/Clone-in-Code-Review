		Label branchLabel = new Label(repositoryGroup, SWT.NONE);
		branchLabel.setText(UIText.SimpleConfigureFetchDialog_BranchLabel);
		String branch;
		try {
			branch = repository.getBranch();
		} catch (IOException e2) {
			branch = null;
		}
		if (branch == null || ObjectId.isId(branch)) {
			branch = UIText.SimpleConfigureFetchDialog_DetachedHeadMessage;
