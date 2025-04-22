		initializeDialogUnits(parent);

		Composite composite = new Composite(parent, SWT.NULL);
		composite.setLayout(new GridLayout());
		composite.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_FILL));
		setControl(composite);

		PlatformUI.getWorkbench().getHelpSystem().setHelp(composite, IIDEHelpContextIds.WORKING_SET_RESOURCE_PAGE);
		Label label = new Label(composite, SWT.WRAP);
		label.setText(IDEWorkbenchMessages.ResourceWorkingSetPage_message);
		GridData data = new GridData(
				GridData.GRAB_HORIZONTAL | GridData.HORIZONTAL_ALIGN_FILL | GridData.VERTICAL_ALIGN_CENTER);
		label.setLayoutData(data);

		text = new Text(composite, SWT.SINGLE | SWT.BORDER);
		text.setLayoutData(new GridData(GridData.GRAB_HORIZONTAL | GridData.HORIZONTAL_ALIGN_FILL));
		text.addModifyListener(e -> validateInput());
		text.setFocus();

		label = new Label(composite, SWT.WRAP);
		label.setText(IDEWorkbenchMessages.ResourceWorkingSetPage_label_tree);
		data = new GridData(GridData.GRAB_HORIZONTAL | GridData.HORIZONTAL_ALIGN_FILL | GridData.VERTICAL_ALIGN_CENTER);
		label.setLayoutData(data);

		tree = new CheckboxTreeViewer(composite);
		tree.setUseHashlookup(true);
