        switch (operationMode) {
        case NEW_VARIABLE:
        	this.standardMessage = IDEWorkbenchMessages.PathVariableDialog_message_newVariable;
        	break;
        case EXISTING_VARIABLE:
        	this.standardMessage = IDEWorkbenchMessages.PathVariableDialog_message_existingVariable;
        	break;
        default:
        	this.standardMessage = IDEWorkbenchMessages.PathVariableDialog_message_editLocation;
        	break;
        }
