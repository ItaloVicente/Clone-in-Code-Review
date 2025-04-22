
	private WizardCollectionElement createCollectionElement(WizardCollectionElement parent,
			IConfigurationElement element) {
		WizardCollectionElement newElement = new WizardCollectionElement(element, parent);

		parent.add(newElement);
		return newElement;
	}

	protected WizardCollectionElement createCollectionElement(WizardCollectionElement parent, String id,
			String pluginId, String label) {
		WizardCollectionElement newElement = new WizardCollectionElement(id, pluginId, label, parent);

		parent.add(newElement);
		return newElement;
	}

	protected void createEmptyWizardCollection() {
		wizardElements = new WizardCollectionElement("root", null, "root", null);//$NON-NLS-2$//$NON-NLS-1$
	}

	public void setInitialCollection(WizardCollectionElement wizards) {
		wizardElements = wizards;
		readAll = false;
	}

	private void deferCategory(IConfigurationElement config) {
		Category category = null;
		try {
			category = new Category(config);
		} catch (CoreException e) {
			WorkbenchPlugin.log("Cannot create category: ", e.getStatus());//$NON-NLS-1$
			return;
