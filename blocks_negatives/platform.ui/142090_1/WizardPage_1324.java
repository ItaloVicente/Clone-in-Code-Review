        return wizard.getContainer();
    }

    /**
     * Returns the dialog settings for this wizard page.
     *
     * @return the dialog settings, or <code>null</code> if none
     */
    protected IDialogSettings getDialogSettings() {
        if (wizard == null) {
