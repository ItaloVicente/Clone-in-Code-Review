		if (showBranchInfo) {
			Label branchLabel = new Label(repositoryGroup, SWT.NONE);
			branchLabel.setText(UIText.SimpleConfigurePushDialog_BranchLabel);
			String branch;
			try {
				branch = repository.getBranch();
			} catch (IOException e2) {
				branch = null;
			}
			if (branch == null || ObjectId.isId(branch)) {
				branch = UIText.SimpleConfigurePushDialog_DetachedHeadMessage;
			}
			Text branchText = new Text(repositoryGroup, SWT.BORDER
					| SWT.READ_ONLY);
			GridDataFactory.fillDefaults().grab(true, false)
					.applyTo(branchText);
			branchText.setText(branch);
