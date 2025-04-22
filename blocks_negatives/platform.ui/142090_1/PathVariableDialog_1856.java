        return allowFinish;
    }

    /**
     * Validates the current variable value, and updates this dialog's message.
     *
     * @return true if the value is valid, false otherwise
     */
    private boolean validateVariableValue() {
        boolean allowFinish = false;

        if (validationStatus == IMessageProvider.ERROR) {
