
	public static WizardCollectionElement filter(Viewer viewer, ViewerFilter viewerFilter,
			WizardCollectionElement inputCollection) {
		AdaptableList wizards = null;

		for (Object child : inputCollection.getWizardAdaptableList().getChildren()) {
			if (viewerFilter.select(viewer, inputCollection, child)) {
				if (wizards == null) {
					wizards = new AdaptableList();
				}
				wizards.add((IAdaptable) child);
			}
		}

		if (wizards == null) {
			return null;
		}
		if (inputCollection.getWizardAdaptableList().size() == wizards.size()) {
			return inputCollection;
		}
		return new WizardCollectionElement(inputCollection, wizards);
	}
