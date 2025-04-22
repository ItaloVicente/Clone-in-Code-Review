        removedVariableNames.add(variableName);
        tempPathVariables.remove(variableName);

        String newVariableName = dialog.getVariableName();
        IPath newVariableValue = new Path(dialog.getVariableValue());

        tempPathVariables.put(newVariableName, newVariableValue);

        updateWidgetState();
        saveVariablesIfRequired();
    }

    /**
     * Returns the enabled state of the group's widgets.
     * Returns <code>true</code> if called prior to calling
     * <code>createContents</code>.
     *
     * @return boolean the enabled state of the group's widgets.
     * 	 <code>true</code> if called prior to calling <code>createContents</code>.
     */
    public boolean getEnabled() {
        if (variableTable != null && !variableTable.getTable().isDisposed()) {
            return variableTable.getTable().getEnabled();
        }
        return true;
    }

    /**
     * Automatically save the path variable list when new variables
     * are added, changed, or removed by the user.
     * @param value
     *
     */
    public void setSaveVariablesOnChange(boolean value) {
    	saveVariablesOnChange = value;
    }

    private void saveVariablesIfRequired() {
    	if (saveVariablesOnChange) {
    		performOk();
    	}
    }
    /**
     * Returns the selected variables.
     *
     * @return the selected variables. Returns an empty array if
     * 	the widget group has not been created yet by calling
     * 	<code>createContents</code>
     */
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

    /**
     * Creates the add/edit/remove buttons
     *
     * @param parent the widget parent
     */
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
