    protected void configureShell(Shell shell) {
    	super.configureShell(shell);
    	switch (operationMode) {
    	case NEW_VARIABLE:
    		shell.setText(IDEWorkbenchMessages.PathVariableDialog_shellTitle_newVariable);
    		break;
    	case EXISTING_VARIABLE:
    		shell.setText(IDEWorkbenchMessages.PathVariableDialog_shellTitle_existingVariable);
    		break;
    	default:
    		shell.setText(IDEWorkbenchMessages.PathVariableDialog_shellTitle_editLocation);
    		break;
    	}
