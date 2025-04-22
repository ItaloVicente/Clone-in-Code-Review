		setInitialTextValues(text);

		Button browseButton = new Button(panel, SWT.PUSH);
		browseButton.setText(IDEWorkbenchMessages.ChooseWorkspaceDialog_browseLabel);
		setButtonLayoutData(browseButton);
		GridData data = (GridData) browseButton.getLayoutData();
		data.horizontalAlignment = GridData.HORIZONTAL_ALIGN_END;
		browseButton.setLayoutData(data);
		browseButton.addSelectionListener(new SelectionAdapter() {
			@Override
