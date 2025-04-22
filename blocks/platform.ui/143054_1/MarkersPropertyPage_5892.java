	private void createResourcePathArea(Composite parent) {
		String containerName = Util.getContainerName(marker);
		if (!containerName.isEmpty()) {
			Label folderLabel = new Label(parent, SWT.NONE);
			folderLabel.setText(MarkerMessages.propertiesDialog_folder_text);
			Text folderText = createReadOnlyText(parent);
			folderText.setText(Util.getContainerName(marker));
		}
	}

	private void createResourceNameArea(Composite parent) {
		Label resourceLabel = new Label(parent, SWT.NONE);
		resourceLabel.setText(MarkerMessages.propertiesDialog_resource_text);
		Text resourceText = createReadOnlyText(parent);
		resourceText.setText(Util.getResourceName(marker));
	}

