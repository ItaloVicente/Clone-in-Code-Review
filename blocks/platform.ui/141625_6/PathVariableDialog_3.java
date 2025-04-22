    	Composite contents = new Composite(parentComposite, SWT.NONE);

    	contents.setLayout(new GridLayout(3, false));
    	contents.setLayoutData(new GridData(GridData.FILL_BOTH));

    	switch (operationMode) {
    	case NEW_VARIABLE:
    		setTitle(IDEWorkbenchMessages.PathVariableDialog_dialogTitle_newVariable);
    		break;
    	case EXISTING_VARIABLE:
    		setTitle(IDEWorkbenchMessages.PathVariableDialog_dialogTitle_existingVariable);
    		break;
    	default:
    		setTitle(IDEWorkbenchMessages.PathVariableDialog_dialogTitle_editLinkLocation);
    		break;
    	}
    	setMessage(standardMessage);
    	return contents;
