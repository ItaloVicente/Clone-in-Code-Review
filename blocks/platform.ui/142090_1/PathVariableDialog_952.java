		super.configureShell(shell);
		if (operationMode == NEW_VARIABLE)
			shell.setText(IDEWorkbenchMessages.PathVariableDialog_shellTitle_newVariable);
		else if (operationMode == EXISTING_VARIABLE)
			shell.setText(IDEWorkbenchMessages.PathVariableDialog_shellTitle_existingVariable);
		else
			shell.setText(IDEWorkbenchMessages.PathVariableDialog_shellTitle_editLocation);
	}

	@Override
