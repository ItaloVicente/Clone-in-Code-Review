        }
        return null;
    }

    /**
     *	Moves given element to "Other" category, previously creating one if missing.
     */
    protected void moveElementToUncategorizedCategory(
            WizardCollectionElement root, WorkbenchWizardElement element) {
        WizardCollectionElement otherCategory = getChildWithID(root,
                UNCATEGORIZED_WIZARD_CATEGORY);

        if (otherCategory == null) {
			otherCategory = createCollectionElement(root,
                    UNCATEGORIZED_WIZARD_CATEGORY, null,
                    UNCATEGORIZED_WIZARD_CATEGORY_LABEL);
