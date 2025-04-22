		if (!showResetType) {
			newButton = new Button(parent, SWT.PUSH);
			newButton.setFont(JFaceResources.getDialogFont());
			newButton.setText(UIText.BranchSelectionDialog_NewBranch);
			setButtonLayoutData(newButton);
			((GridLayout)parent.getLayout()).numColumns++;
