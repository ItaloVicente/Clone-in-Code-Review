        String message = standardMessage;
        int newValidationStatus = IMessageProvider.NONE;

        if (variableName.length() == 0) {
            if (nameEntered) {
                newValidationStatus = IMessageProvider.ERROR;
                message = IDEWorkbenchMessages.PathVariableDialog_variableNameEmptyMessage;
            }
        } else {
            IStatus status = pathVariableManager.validateName(variableName);
            if (!status.isOK()) {
                newValidationStatus = IMessageProvider.ERROR;
                message = status.getMessage();
            } else if (namesInUse.contains(variableName)
                    && !variableName.equals(originalName)) {
                message = IDEWorkbenchMessages.PathVariableDialog_variableAlreadyExistsMessage;
                newValidationStatus = IMessageProvider.ERROR;
            } else {
                allowFinish = true;
            }
        }

        if (validationStatus == IMessageProvider.NONE
                || newValidationStatus == IMessageProvider.ERROR) {
            validationStatus = newValidationStatus;
            validationMessage = message;
        }
        if (allowFinish == false) {
