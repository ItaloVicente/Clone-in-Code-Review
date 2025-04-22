	@SuppressWarnings("unchecked")
	private WizardCollectionElement(WizardCollectionElement input, AdaptableList wizards) {
		this(input.id, input.pluginId, input.name, input.parent);
		this.configElement = input.configElement;
		this.wizards = wizards;

		for (Object child : input.children) {
			children.add(child);
		}
	}

