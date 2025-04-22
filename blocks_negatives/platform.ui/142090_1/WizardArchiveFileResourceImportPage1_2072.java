    }

    /**
     * Called when the user presses the Cancel button. Return a boolean
     * indicating permission to close the wizard.
     *
     * @return boolean
     */
    public boolean cancel() {
        disposeStructureProvider();
        return true;
    }

    @Override
