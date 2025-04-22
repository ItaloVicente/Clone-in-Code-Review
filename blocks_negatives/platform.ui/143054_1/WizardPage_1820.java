        return wizard;
    }

    /**
     * Returns whether this page is the current one in the wizard's container.
     *
     * @return <code>true</code> if the page is active,
     *  and <code>false</code> otherwise
     */
    protected boolean isCurrentPage() {
        return (getContainer() != null && this == getContainer()
                .getCurrentPage());
    }

    /**
     * The <code>WizardPage</code> implementation of this <code>IWizard</code> method
     * returns the value of an internal state variable set by
     * <code>setPageComplete</code>. Subclasses may extend.
     */
    @Override
