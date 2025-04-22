		Composite parentComposite = (Composite) super.createDialogArea(parent);

		Composite composite = new Composite(parentComposite, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.marginHeight = convertVerticalDLUsToPixels(IDialogConstants.VERTICAL_MARGIN);
		layout.marginWidth = convertHorizontalDLUsToPixels(IDialogConstants.HORIZONTAL_MARGIN);
		layout.verticalSpacing = convertVerticalDLUsToPixels(IDialogConstants.VERTICAL_SPACING);
		layout.horizontalSpacing = convertHorizontalDLUsToPixels(IDialogConstants.HORIZONTAL_SPACING);
		composite.setLayout(layout);
		composite.setLayoutData(new GridData(GridData.FILL_BOTH));
		composite.setFont(parentComposite.getFont());

		Listener listener = event -> setDialogComplete(validatePage());

		resourceGroup = new ResourceAndContainerGroup(composite, listener, IDEWorkbenchMessages.SaveAsDialog_fileLabel,
				IDEWorkbenchMessages.SaveAsDialog_file);
		resourceGroup.setAllowExistingResources(true);

		return parentComposite;
	}

	public IPath getResult() {
		return result;
	}

	private void initializeControls() {
		if (originalFile != null) {
			resourceGroup.setContainerFullPath(originalFile.getParent().getFullPath());
			resourceGroup.setResource(originalFile.getName());
		} else if (originalName != null) {
