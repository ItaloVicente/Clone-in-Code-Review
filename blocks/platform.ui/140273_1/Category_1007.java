	public Category() {
		this.id = MISC_ID;
		this.name = MISC_NAME;
		this.pluginId = MISC_ID; // TODO: remove hack for bug 55172
	}

	public Category(String id, String label) {
		this.id = id;
		this.name = label;
	}

	public Category(IConfigurationElement configElement) throws WorkbenchException {
		id = configElement.getAttribute(IWorkbenchRegistryConstants.ATT_ID);

		configurationElement = configElement;
		if (id == null || getLabel() == null) {
