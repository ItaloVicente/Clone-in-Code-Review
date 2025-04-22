            return true;
        }
    }

    /**
     * Reads the wizards in a registry.
     * <p>
     * This implementation uses a defering strategy.  All of the elements
     * (categories, wizards) are read.  The categories are created as the read occurs.
     * The wizards are just stored for later addition after the read completes.
     * This ensures that wizard categorization is performed after all categories
     * have been read.
     * </p>
     */
    protected void readWizards() {
    	if (readAll) {
    	       if (!areWizardsRead()) {
                createEmptyWizardCollection();
                IExtensionRegistry registry = Platform.getExtensionRegistry();
                readRegistry(registry, plugin, pluginPoint);
            }
    	}
        finishCategories();
        finishWizards();
        finishPrimary();
        if (wizardElements != null) {
            pruneEmptyCategories(wizardElements);
        }
    }

    /**
     * Returns the list of wizards that are considered 'primary'.
     *
     * The return value for this method is cached since computing its value
     * requires non-trivial work.
     *
     * @return the primary wizards
     */
    public WorkbenchWizardElement [] getPrimaryWizards() {
        if (!areWizardsRead()) {
            readWizards();
        }
        return (WorkbenchWizardElement[]) WorkbenchActivityHelper.restrictArray(primaryWizards);
    }


    /**
     * Returns whether the wizards have been read already
     */
    protected boolean areWizardsRead() {
        return wizardElements != null && readAll;
    }

    /**
     * Returns a list of wizards, project and not.
     *
     * The return value for this method is cached since computing its value
     * requires non-trivial work.
     *
     * @return the wizard collection
     */
    public WizardCollectionElement getWizardElements() {
        if (!areWizardsRead()) {
            readWizards();
        }
        return wizardElements;
    }

    protected Object[] getWizardCollectionElements() {
        if (!areWizardsRead()) {
            readWizards();
        }
        return wizardElements.getChildren();
    }

    /**
     * Returns a new WorkbenchWizardElement configured according to the parameters
     * contained in the passed Registry.
     *
     * May answer null if there was not enough information in the Extension to create
     * an adequate wizard
     */
    protected WorkbenchWizardElement createWizardElement(
            IConfigurationElement element) {
        if (element.getAttribute(IWorkbenchRegistryConstants.ATT_NAME) == null) {
            logMissingAttribute(element, IWorkbenchRegistryConstants.ATT_NAME);
            return null;
        }

        if (getClassValue(element, IWorkbenchRegistryConstants.ATT_CLASS) == null) {
            logMissingAttribute(element, IWorkbenchRegistryConstants.ATT_CLASS);
            return null;
        }
        return new WorkbenchWizardElement(element);
    }

    /**
     * Returns the first wizard with a given id.
     *
     * @param id wizard id to search for
     * @return WorkbenchWizardElement matching the given id, if found; null otherwise
     */
    public WorkbenchWizardElement findWizard(String id) {
