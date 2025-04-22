        Composite contents = new Composite(parentComposite, SWT.NONE);

        contents.setLayout(new GridLayout(3, false));
        contents.setLayoutData(new GridData(GridData.FILL_BOTH));

        if (operationMode == NEW_VARIABLE)
            setTitle(IDEWorkbenchMessages.PathVariableDialog_dialogTitle_newVariable);
        else if (operationMode == EXISTING_VARIABLE)
            setTitle(IDEWorkbenchMessages.PathVariableDialog_dialogTitle_existingVariable);
        else
            setTitle(IDEWorkbenchMessages.PathVariableDialog_dialogTitle_editLinkLocation);
        setMessage(standardMessage);
        return contents;
