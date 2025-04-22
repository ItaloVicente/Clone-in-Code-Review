		branchText.setEnabled(createBranchSelected);
		branchText.setVisible(createBranchSelected);
		branchTextlabel.setVisible(createBranchSelected);
		GridData gd = (GridData) branchText.getLayoutData();
		gd.exclude = !createBranchSelected;
		gd = (GridData) branchTextlabel.getLayoutData();
		gd.exclude = !createBranchSelected;
