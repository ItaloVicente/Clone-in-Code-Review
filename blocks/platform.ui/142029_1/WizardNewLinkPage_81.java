		Font font = parent.getFont();
		initializeDialogUnits(parent);
		Composite topLevel = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.numColumns = 3;
		topLevel.setLayout(layout);
		topLevel.setLayoutData(new GridData(GridData.VERTICAL_ALIGN_FILL | GridData.HORIZONTAL_ALIGN_FILL));
		topLevel.setFont(font);
		PlatformUI.getWorkbench().getHelpSystem().setHelp(topLevel, IIDEHelpContextIds.NEW_LINK_WIZARD_PAGE);

		final Button createLinkButton = new Button(topLevel, SWT.CHECK);
		if (type == IResource.FILE) {
