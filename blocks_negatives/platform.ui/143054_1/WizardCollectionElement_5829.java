		return (IWizardCategory []) getTypedChildren(IWizardCategory.class);
	}

    /**
     * Return the collection elements.
     *
     * @return the collection elements
     * @since 3.1
     */
    public WizardCollectionElement [] getCollectionElements() {
    	return (WizardCollectionElement[]) getTypedChildren(WizardCollectionElement.class);
    }

    /**
     * Return the raw adapted list of wizards.
     *
     * @return the list of wizards
     * @since 3.1
     */
    public AdaptableList getWizardAdaptableList() {
    	return wizards;
    }

    @Override
