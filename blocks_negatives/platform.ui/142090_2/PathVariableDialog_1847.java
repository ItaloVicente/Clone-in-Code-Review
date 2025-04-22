        super.configureShell(shell);
        if (operationMode == NEW_VARIABLE)
            shell.setText(IDEWorkbenchMessages.PathVariableDialog_shellTitle_newVariable);
        else if (operationMode == EXISTING_VARIABLE)
            shell.setText(IDEWorkbenchMessages.PathVariableDialog_shellTitle_existingVariable);
        else
            shell.setText(IDEWorkbenchMessages.PathVariableDialog_shellTitle_editLocation);
    }

    /**
     * Creates and returns the contents of this dialog (except for the button bar).
     *
     * @see org.eclipse.jface.dialogs.TitleAreaDialog#createDialogArea
     */
    @Override
