		removedVariableNames.add(variableName);
		tempPathVariables.remove(variableName);

		String newVariableName = dialog.getVariableName();
		IPath newVariableValue = new Path(dialog.getVariableValue());

		tempPathVariables.put(newVariableName, newVariableValue);

		updateWidgetState();
		saveVariablesIfRequired();
	}

	public boolean getEnabled() {
		if (variableTable != null && !variableTable.getTable().isDisposed()) {
			return variableTable.getTable().getEnabled();
		}
		return true;
	}

	public void setSaveVariablesOnChange(boolean value) {
		saveVariablesOnChange = value;
	}

	private void saveVariablesIfRequired() {
		if (saveVariablesOnChange) {
			performOk();
		}
	}
	public PathVariableElement[] getSelection() {
		if (variableTable == null) {
			return new PathVariableElement[0];
		}
		TableItem[] items = variableTable.getTable().getSelection();
		PathVariableElement[] selection = new PathVariableElement[items.length];

		for (int i = 0; i < items.length; i++) {
			String name = (String) items[i].getData();
			selection[i] = new PathVariableElement();
			selection[i].name = name;
			selection[i].path = tempPathVariables.get(name);
		}
		return selection;
	}

	private void createButtonGroup(Composite parent) {
		Font font = parent.getFont();
		Composite groupComponent = new Composite(parent, SWT.NULL);
		GridLayout groupLayout = new GridLayout();
		groupLayout.marginWidth = 0;
		groupLayout.marginHeight = 0;
		groupComponent.setLayout(groupLayout);
		GridData data = new GridData();
		data.verticalAlignment = GridData.FILL;
		data.horizontalAlignment = GridData.FILL;
		groupComponent.setLayoutData(data);
		groupComponent.setFont(font);

		addButton = new Button(groupComponent, SWT.PUSH);
		addButton.setText(IDEWorkbenchMessages.PathVariablesBlock_addVariableButton);
		addButton.addSelectionListener(new SelectionAdapter() {
			@Override
