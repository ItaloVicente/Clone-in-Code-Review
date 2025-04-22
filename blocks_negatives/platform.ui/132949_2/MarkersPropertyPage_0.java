		Label resourceLabel = new Label(parent, SWT.NONE);
		resourceLabel.setText(MarkerMessages.propertiesDialog_resource_text);
		Text resourceText = new Text(parent, SWT.SINGLE | SWT.WRAP | SWT.READ_ONLY | SWT.BORDER);
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		resourceText.setLayoutData(gridData);
		resourceText.setText(Util.getResourceName(marker));

		Label folderLabel = new Label(parent, SWT.NONE);
		folderLabel.setText(MarkerMessages.propertiesDialog_folder_text);
		Text folderText = new Text(parent, SWT.SINGLE | SWT.WRAP | SWT.READ_ONLY | SWT.BORDER);
		gridData = new GridData(GridData.FILL_HORIZONTAL);
		folderText.setLayoutData(gridData);
		folderText.setText(Util.getContainerName(marker));
