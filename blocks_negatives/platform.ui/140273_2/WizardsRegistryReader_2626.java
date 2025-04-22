    protected void addNewElementToResult(WorkbenchWizardElement element,
            IConfigurationElement config) {
        deferWizard(element);
    }

    /**
     *
     * @param parent
     * @param element
     * @since 3.1
     */
    private WizardCollectionElement createCollectionElement(WizardCollectionElement parent, IConfigurationElement element) {
        WizardCollectionElement newElement = new WizardCollectionElement(
				element, parent);

        parent.add(newElement);
        return newElement;
