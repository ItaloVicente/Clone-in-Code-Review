    }

    /**
     * Updates the enabled state of the Extend button based on the
     * current variable selection.
     */
    private void updateExtendButtonState() {
        PathVariablesGroup.PathVariableElement[] selection = pathVariablesGroup
                .getSelection();
        Button extendButton = getButton(EXTEND_ID);

        if (extendButton == null) {
