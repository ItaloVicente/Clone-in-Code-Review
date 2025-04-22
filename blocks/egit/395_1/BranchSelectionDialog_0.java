			Button newBranchButton = new Button(parent, SWT.PUSH);
			newBranchButton.setFont(JFaceResources.getDialogFont());
			newBranchButton.setText(UIText.BranchSelectionDialog_NewBranch);
			((GridLayout)parent.getLayout()).numColumns++;
			Button newTagButton = new Button(parent, SWT.PUSH);
			newTagButton.setFont(JFaceResources.getDialogFont());
			newTagButton.setText(UIText.BranchSelectionDialog_NewTag);
