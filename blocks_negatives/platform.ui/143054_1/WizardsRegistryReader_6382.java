            currentCollectionElement.add(element);
            element.setParent(currentCollectionElement);
        }
    }

    /**
     * Finishes the addition of wizards.  The wizards are processed and categorized.
     */
    private void finishWizards() {
        if (deferWizards != null) {
            Iterator iter = deferWizards.iterator();
            while (iter.hasNext()) {
                WorkbenchWizardElement wizard = (WorkbenchWizardElement) iter
                        .next();
                IConfigurationElement config = wizard.getConfigurationElement();
                finishWizard(wizard, config);
            }
            deferWizards = null;
        }
    }

    /**
     *	Return the appropriate category (tree location) for this Wizard.
     *	If a category is not specified then return a default one.
     */
    protected String getCategoryStringFor(IConfigurationElement config) {
        String result = config.getAttribute(IWorkbenchRegistryConstants.TAG_CATEGORY);
        if (result == null) {
