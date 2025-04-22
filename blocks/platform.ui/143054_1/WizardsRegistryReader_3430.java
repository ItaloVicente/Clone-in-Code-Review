			currentCollectionElement.add(element);
			element.setParent(currentCollectionElement);
		}
	}

	private void finishWizards() {
		if (deferWizards != null) {
			Iterator iter = deferWizards.iterator();
			while (iter.hasNext()) {
				WorkbenchWizardElement wizard = (WorkbenchWizardElement) iter.next();
				IConfigurationElement config = wizard.getConfigurationElement();
				finishWizard(wizard, config);
			}
			deferWizards = null;
		}
	}

	protected String getCategoryStringFor(IConfigurationElement config) {
		String result = config.getAttribute(IWorkbenchRegistryConstants.TAG_CATEGORY);
		if (result == null) {
