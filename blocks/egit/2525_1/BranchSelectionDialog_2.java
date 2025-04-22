		deleteteButton = new Button(parent, SWT.PUSH);
		deleteteButton.setFont(JFaceResources.getDialogFont());
		deleteteButton.setText(UIText.BranchSelectionDialog_Delete);
		setButtonLayoutData(deleteteButton);
		((GridLayout) parent.getLayout()).numColumns++;

