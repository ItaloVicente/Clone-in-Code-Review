	private void finishWizard(WorkbenchWizardElement element, IConfigurationElement config) {
		StringTokenizer familyTokenizer = new StringTokenizer(getCategoryStringFor(config), CATEGORY_SEPARATOR);

		WizardCollectionElement currentCollectionElement = wizardElements; // ie.- root
		boolean moveToOther = false;

		while (familyTokenizer.hasMoreElements()) {
			WizardCollectionElement tempCollectionElement = getChildWithID(currentCollectionElement,
					familyTokenizer.nextToken());

			if (tempCollectionElement == null) { // can't find the path; bump it to uncategorized
				moveToOther = true;
				break;
			}
			currentCollectionElement = tempCollectionElement;
		}

		if (moveToOther) {
