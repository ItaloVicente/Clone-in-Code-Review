        okButton = createButton(parent, IDialogConstants.OK_ID,
                IDialogConstants.OK_LABEL, true);
        okButton.setEnabled(type == EXISTING_VARIABLE);

        createButton(parent, IDialogConstants.CANCEL_ID,
                IDialogConstants.CANCEL_LABEL, false);
    }

    /**
     * Validates the current variable name, and updates this dialog's message.
     *
     * @return true if the name is valid, false otherwise
     */
    private boolean validateVariableName() {
        boolean allowFinish = false;

        if (operationMode == EDIT_LINK_LOCATION)
            return true;

        if (validationStatus == IMessageProvider.ERROR) {
