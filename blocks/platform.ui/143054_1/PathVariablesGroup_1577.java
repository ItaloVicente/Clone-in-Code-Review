		String newVariableName = dialog.getVariableName();
		IPath newVariableValue = new Path(dialog.getVariableValue());
		tempPathVariables.put(newVariableName, newVariableValue);

		updateWidgetState();
		saveVariablesIfRequired();
	}

	public Control createContents(Composite parent) {
		Font font = parent.getFont();

		if (imageUnkown == null) {
			ImageDescriptor descriptor = AbstractUIPlugin
					.imageDescriptorFromPlugin(
							IDEWorkbenchPlugin.IDE_WORKBENCH,
							"$nl$/icons/full/obj16/warning.png"); //$NON-NLS-1$
			imageUnkown = descriptor.createImage();
		}
		initializeDialogUnits(parent);
		shell = parent.getShell();

		Composite pageComponent = new Composite(parent, SWT.NULL);
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		layout.marginWidth = 0;
		layout.marginHeight = 0;
		pageComponent.setLayout(layout);
		GridData data = new GridData(GridData.FILL_BOTH);
		data.widthHint = SIZING_SELECTION_PANE_WIDTH;
		pageComponent.setLayoutData(data);
		pageComponent.setFont(font);

		variableLabel = new Label(pageComponent, SWT.LEFT);
		if (currentResource == null)
			variableLabel.setText(IDEWorkbenchMessages.PathVariablesBlock_variablesLabel);
		else
			variableLabel.setText(NLS.bind(
									IDEWorkbenchMessages.PathVariablesBlock_variablesLabelForResource,
									currentResource.getName()));

		data = new GridData();
		data.horizontalAlignment = GridData.FILL;
		data.horizontalSpan = 2;
		variableLabel.setLayoutData(data);
		variableLabel.setFont(font);

		int tableStyle = SWT.BORDER | SWT.FULL_SELECTION;
		if (multiSelect) {
			tableStyle |= SWT.MULTI;
		}
