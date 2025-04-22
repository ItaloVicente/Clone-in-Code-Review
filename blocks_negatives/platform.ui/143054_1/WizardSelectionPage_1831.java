        super.dispose();
        for (int i = 0; i < selectedWizardNodes.size(); i++) {
            selectedWizardNodes.get(i).dispose();
        }
    }

    /**
     * The <code>WizardSelectionPage</code> implementation of
     * this <code>IWizardPage</code> method returns the first page
     * of the currently selected wizard if there is one.
     */
    @Override
