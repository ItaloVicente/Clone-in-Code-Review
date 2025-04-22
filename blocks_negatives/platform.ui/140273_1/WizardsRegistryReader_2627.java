    /**
     *	Create and answer a new WizardCollectionElement, configured as a
     *	child of <code>parent</code>
     *
     *	@return org.eclipse.ui.internal.model.WizardCollectionElement
     *	@param parent org.eclipse.ui.internal.model.WizardCollectionElement
     *  @param id the id of the new collection
     *  @param pluginId the originating plugin id of the collection, if any. <code>null</code> otherwise.
     *	@param label java.lang.String
     */
    protected WizardCollectionElement createCollectionElement(
            WizardCollectionElement parent, String id, String pluginId,
            String label) {
        WizardCollectionElement newElement = new WizardCollectionElement(id,
                pluginId, label, parent);

        parent.add(newElement);
        return newElement;
    }

    /**
     * Creates empty element collection. Overrider to fill
     * initial elements, if needed.
     */
    protected void createEmptyWizardCollection() {
        wizardElements = new WizardCollectionElement("root", null, "root", null);//$NON-NLS-2$//$NON-NLS-1$
    }

    /**
     * Set the initial wizard set for supplemental reading via dynamic plugin loading.
     *
     * @param wizards the wizards
     * @since 3.1
     */
    public void setInitialCollection(WizardCollectionElement wizards) {
    	wizardElements = wizards;
    	readAll = false;
    }

    /**
     * Stores a category element for deferred addition.
     */
    private void deferCategory(IConfigurationElement config) {
        Category category = null;
        try {
            category = new Category(config);
        } catch (CoreException e) {
            WorkbenchPlugin.log("Cannot create category: ", e.getStatus());//$NON-NLS-1$
            return;
        }

        if (deferCategories == null) {
			deferCategories = new ArrayList(20);
