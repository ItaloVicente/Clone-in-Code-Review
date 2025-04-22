		return (IWizardCategory[]) getTypedChildren(IWizardCategory.class);
	}

	public WizardCollectionElement[] getCollectionElements() {
		return (WizardCollectionElement[]) getTypedChildren(WizardCollectionElement.class);
	}

	public AdaptableList getWizardAdaptableList() {
		return wizards;
	}

	@Override
