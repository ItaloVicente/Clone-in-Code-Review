		return null;
	}

	protected void moveElementToUncategorizedCategory(WizardCollectionElement root, WorkbenchWizardElement element) {
		WizardCollectionElement otherCategory = getChildWithID(root, UNCATEGORIZED_WIZARD_CATEGORY);

		if (otherCategory == null) {
			otherCategory = createCollectionElement(root, UNCATEGORIZED_WIZARD_CATEGORY, null,
					UNCATEGORIZED_WIZARD_CATEGORY_LABEL);
		}

		otherCategory.add(element);
		element.setParent(otherCategory);
	}
