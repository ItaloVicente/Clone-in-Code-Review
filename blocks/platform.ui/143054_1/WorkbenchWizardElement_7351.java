	public WorkbenchWizardElement(IConfigurationElement configurationElement) {
		this.configurationElement = configurationElement;
		id = configurationElement.getAttribute(IWorkbenchRegistryConstants.ATT_ID);
	}

	public boolean canHandleSelection(IStructuredSelection selection) {
		return getSelectionEnabler().isEnabledForSelection(selection);
	}
