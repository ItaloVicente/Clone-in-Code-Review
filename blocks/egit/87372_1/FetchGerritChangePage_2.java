		boolean hasLocalBranch = getLocalRef(refText.getText()) != null;
		changeBranch.setEnabled(hasLocalBranch);
		changeBranch.setVisible(hasLocalBranch);
		GridData gd = (GridData) changeBranch.getLayoutData();
		gd.exclude = !hasLocalBranch;

