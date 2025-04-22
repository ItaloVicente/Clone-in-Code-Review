			WizardCollectionElement currentCategory = (WizardCollectionElement) element;
			if (id.equals(currentCategory.getId())) {
				return currentCategory;
			}
			WizardCollectionElement childCategory = currentCategory.findCategory(id);
			if (childCategory != null) {
				return childCategory;
			}
		}
		return null;
	}

	public WorkbenchWizardElement findWizard(String searchId, boolean recursive) {
