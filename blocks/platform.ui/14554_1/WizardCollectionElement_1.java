
	@Override
	public Object clone() {
		WizardCollectionElement copy = new WizardCollectionElement(id, pluginId, name, parent);
		copy.configElement = configElement;

		for (Object child: wizards.getChildren()) {
			if (child instanceof IAdaptable) {
				copy.wizards.add((IAdaptable) child);
			}
		}
		return copy;
	}
