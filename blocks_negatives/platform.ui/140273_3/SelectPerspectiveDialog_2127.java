    }

    /**
     * Update the button enablement state.
     */
    protected void updateButtons() {
        okButton.setEnabled(getSelection() != null);
    }

    /**
     * Update the selection object.
     */
    protected void updateSelection(SelectionChangedEvent event) {
        perspDesc = null;
