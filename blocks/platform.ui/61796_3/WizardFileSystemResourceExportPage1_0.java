	protected void createResolveLinkedResources(Composite parent, Font font) {
		resolveLinkedResourcesCheckbox = new Button(parent, SWT.CHECK | SWT.LEFT);
		resolveLinkedResourcesCheckbox.setText(DataTransferMessages.ExportFile_resolveLinkedResources);
		resolveLinkedResourcesCheckbox.setFont(font);
	}

