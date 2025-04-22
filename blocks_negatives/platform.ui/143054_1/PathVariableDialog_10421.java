        String message = standardMessage;
        int newValidationStatus = IMessageProvider.NONE;

        if (variableValue.length() == 0) {
            if (locationEntered) {
                newValidationStatus = IMessageProvider.ERROR;
                message = IDEWorkbenchMessages.PathVariableDialog_variableValueEmptyMessage;
            }
        }
        if (currentResource != null) {
            allowFinish = true;
            String resolvedValue = getVariableResolvedValue();
            IPath resolvedPath = Path.fromOSString(resolvedValue);
            if (!IDEResourceInfoUtils.exists(resolvedPath.toOSString())) {
                message = IDEWorkbenchMessages.PathVariableDialog_pathDoesNotExistMessage;
                newValidationStatus = IMessageProvider.WARNING;
            } else {
                IFileInfo info = IDEResourceInfoUtils.getFileInfo(resolvedPath
                        .toOSString());
                if ((info.isDirectory() && ((variableType & IResource.FOLDER) == 0)) ||
                		(!info.isDirectory() && ((variableType & IResource.FILE) == 0))){
                    allowFinish = false;
                    newValidationStatus = IMessageProvider.ERROR;
                    if (((variableType & IResource.FOLDER) != 0))
                        message = IDEWorkbenchMessages.PathVariableDialog_variableValueIsWrongTypeFolder;
                    else
                        message = IDEWorkbenchMessages.PathVariableDialog_variableValueIsWrongTypeFile;
                }
            }
        } else if (!Path.EMPTY.isValidPath(variableValue)) {
            message = IDEWorkbenchMessages.PathVariableDialog_variableValueInvalidMessage;
            newValidationStatus = IMessageProvider.ERROR;
        } else if (!new Path(variableValue).isAbsolute()) {
            message = IDEWorkbenchMessages.PathVariableDialog_pathIsRelativeMessage;
            newValidationStatus = IMessageProvider.ERROR;
        } else if (!IDEResourceInfoUtils.exists(variableValue)) {
            message = IDEWorkbenchMessages.PathVariableDialog_pathDoesNotExistMessage;
            newValidationStatus = IMessageProvider.WARNING;
            allowFinish = true;
        } else {
            allowFinish = true;
        }

        if (validationStatus == IMessageProvider.NONE
                || newValidationStatus > validationStatus) {
            validationStatus = newValidationStatus;
            validationMessage = message;
        }
        setMessage(validationMessage, validationStatus);
        return allowFinish;
    }

    /**
     * Returns the variable name.
     *
     * @return the variable name
     */
    public String getVariableName() {
        return variableName;
    }

    /**
     * Returns the variable value.
     *
     * @return the variable value
     */
    public String getVariableValue() {
    	if (currentResource != null) {
    		String internalFormat = getPathVariableManager().convertFromUserEditableFormat(variableValue, operationMode == EDIT_LINK_LOCATION);
    		return internalFormat;
    	}
    	return variableValue;
    }

    /**
     * Sets the variable name.
     *
     * @param variableName the new variable name
     */
    public void setVariableName(String variableName) {
        this.variableName = variableName.trim();
        this.originalName = this.variableName;
    }

    /**
     * Sets the variable value.
     *
     * @param variable the new variable value
     */
    public void setVariableValue(String variable) {
    	String userEditableString = getPathVariableManager().convertToUserEditableFormat(variable, operationMode == EDIT_LINK_LOCATION);
        variableValue = userEditableString;
    }

    /**
     * @param resource
     */
    public void setResource(IResource resource) {
    	currentResource = resource;
    }

    /**
     * @param location
     */
    public void setLinkLocation(IPath location) {
    	String userEditableString = getPathVariableManager().convertToUserEditableFormat(location.toOSString(), operationMode == EDIT_LINK_LOCATION);
        variableValue = userEditableString;
    }

    @Override
