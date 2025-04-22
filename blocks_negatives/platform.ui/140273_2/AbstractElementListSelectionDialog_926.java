        updateOkState();
    }

    /**
     * Update the enablement of the OK button based on whether or not there
     * is a selection.
     *
     */
    protected void updateOkState() {
        Button okButton = getOkButton();
        if (okButton != null) {
