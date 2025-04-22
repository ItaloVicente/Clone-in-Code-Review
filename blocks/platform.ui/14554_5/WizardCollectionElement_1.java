
	@Override
	public Object clone() {
		WizardCollectionElement copy = new WizardCollectionElement(id, pluginId, name, parent);
		copy.configElement = configElement;

		for (Object child : wizards.getChildren()) {
			if (child instanceof IAdaptable) {
				copy.wizards.add((IAdaptable) child);
			}
		}
		for (Object child : children) {
			copy.children.add(child);
		}

		return copy;
	}
