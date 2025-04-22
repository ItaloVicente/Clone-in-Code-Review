        return wizard.getPreviousPage(this);
    }

    /**
     * The <code>WizardPage</code> implementation of this method declared on
     * <code>DialogPage</code> returns the shell of the container.
     * The advantage of this implementation is that the shell is accessable
     * once the container is created even though this page's control may not
     * yet be created.
     */
    @Override
