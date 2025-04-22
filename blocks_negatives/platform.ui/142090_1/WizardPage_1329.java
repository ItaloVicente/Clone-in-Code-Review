        super.setErrorMessage(newMessage);
        if (isCurrentPage()) {
            getContainer().updateMessage();
        }
    }

    /**
     * The <code>WizardPage</code> implementation of this method
     * declared on <code>DialogPage</code> updates the container
     * if this page is the current page.
     */
    @Override
