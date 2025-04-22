	public WizardCollectionElement(String id, String pluginId, String name, WizardCollectionElement parent) {
		this.name = name;
		this.id = id;
		this.pluginId = pluginId;
		this.parent = parent;
	}

	public WizardCollectionElement(IConfigurationElement element, WizardCollectionElement parent) {
