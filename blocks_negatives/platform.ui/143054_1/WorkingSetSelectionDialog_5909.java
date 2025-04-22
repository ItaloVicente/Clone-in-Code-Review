    }

    /**
     * Called when the selection has changed.
     */
    void handleSelectionChanged() {
        updateButtonAvailability();
    }

    /**
     * Sets the selected working sets as the dialog result.
     * Overrides method from Dialog
     *
     * @see org.eclipse.jface.dialogs.Dialog#okPressed()
     */
    @Override
