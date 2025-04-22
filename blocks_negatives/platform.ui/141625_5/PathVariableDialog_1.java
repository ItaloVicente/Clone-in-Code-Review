        if (operationMode == NEW_VARIABLE)
            this.standardMessage = IDEWorkbenchMessages.PathVariableDialog_message_newVariable;
        else if (operationMode == EXISTING_VARIABLE)
            this.standardMessage = IDEWorkbenchMessages.PathVariableDialog_message_existingVariable;
        else
            this.standardMessage = IDEWorkbenchMessages.PathVariableDialog_message_editLocation;
