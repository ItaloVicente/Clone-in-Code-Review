			return true;
		}
	}

	protected void readWizards() {
		if (readAll) {
			if (!areWizardsRead()) {
				createEmptyWizardCollection();
				IExtensionRegistry registry = Platform.getExtensionRegistry();
				readRegistry(registry, plugin, pluginPoint);
			}
		}
		finishCategories();
		finishWizards();
		finishPrimary();
		if (wizardElements != null) {
			pruneEmptyCategories(wizardElements);
		}
	}

	public WorkbenchWizardElement[] getPrimaryWizards() {
		if (!areWizardsRead()) {
			readWizards();
		}
		return (WorkbenchWizardElement[]) WorkbenchActivityHelper.restrictArray(primaryWizards);
	}

	protected boolean areWizardsRead() {
		return wizardElements != null && readAll;
	}

	public WizardCollectionElement getWizardElements() {
		if (!areWizardsRead()) {
			readWizards();
		}
		return wizardElements;
	}

	protected Object[] getWizardCollectionElements() {
		if (!areWizardsRead()) {
			readWizards();
		}
		return wizardElements.getChildren();
	}

	protected WorkbenchWizardElement createWizardElement(IConfigurationElement element) {
		if (element.getAttribute(IWorkbenchRegistryConstants.ATT_NAME) == null) {
			logMissingAttribute(element, IWorkbenchRegistryConstants.ATT_NAME);
			return null;
		}

		if (getClassValue(element, IWorkbenchRegistryConstants.ATT_CLASS) == null) {
			logMissingAttribute(element, IWorkbenchRegistryConstants.ATT_CLASS);
			return null;
		}
		return new WorkbenchWizardElement(element);
	}

	public WorkbenchWizardElement findWizard(String id) {
