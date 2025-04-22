        return isPageComplete() && getNextPage() != null;
    }

    /**
     * Returns the wizard container for this wizard page.
     *
     * @return the wizard container, or <code>null</code> if this
     *   wizard page has yet to be added to a wizard, or the
     *   wizard has yet to be added to a container
     */
    protected IWizardContainer getContainer() {
        if (wizard == null) {
