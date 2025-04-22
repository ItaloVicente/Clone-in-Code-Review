	private void createResourcePathArea(Composite parent) {
		String containerName = Util.getContainerName(marker);
		if (!containerName.isEmpty()) {
			GridData gridData;
			Label folderLabel = new Label(parent, SWT.NONE);
			folderLabel.setText(MarkerMessages.propertiesDialog_folder_text);
			Text folderText = new Text(parent, SWT.READ_ONLY);
			folderText.setBackground(folderText.getDisplay().getSystemColor(SWT.COLOR_WIDGET_BACKGROUND));
			gridData = new GridData(GridData.FILL_HORIZONTAL);
			folderText.setLayoutData(gridData);
			folderText.setText(Util.getContainerName(marker));
		}
	}

	private void createResourceNameArea(Composite parent) {
		Label resourceLabel = new Label(parent, SWT.NONE);
		resourceLabel.setText(MarkerMessages.propertiesDialog_resource_text);
		Text resourceText = new Text(parent, SWT.READ_ONLY);
		resourceText.setBackground(resourceText.getDisplay().getSystemColor(SWT.COLOR_WIDGET_BACKGROUND));
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		resourceText.setLayoutData(gridData);
		resourceText.setText(Util.getResourceName(marker));
	}

