        if (sel.size() > 0) {
            WorkbenchWizardElement selectedWizard = (WorkbenchWizardElement) sel
                    .getFirstElement();
            getDialogSettings().put(STORE_SELECTED_WIZARD_ID,
                    selectedWizard.getId());
        }
    }

    /**
     * Notes the newly-selected wizard element and updates the page
     * accordingly.
     *
     * @param event the selection changed event
     */
    @Override
