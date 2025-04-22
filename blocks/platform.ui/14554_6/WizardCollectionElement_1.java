
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

	public static WizardCollectionElement filter(Viewer viewer, ViewerFilter viewerFilter,
			WizardCollectionElement inputCollection) {
		WizardCollectionElement modifiedCollection = null;

		for (Object child : inputCollection.getWizardAdaptableList().getChildren()) {
			if (!viewerFilter.select(viewer, inputCollection, child)) {
				if (modifiedCollection == null) {
					modifiedCollection = (WizardCollectionElement) inputCollection.clone();
				}
				modifiedCollection.getWizardAdaptableList().remove((IAdaptable) child);
			}
		}

		if (modifiedCollection == null) {
			return inputCollection;
		}
		if (modifiedCollection.getWizardAdaptableList().size() == 0) {
			return null;
		}
		return modifiedCollection;
	}
