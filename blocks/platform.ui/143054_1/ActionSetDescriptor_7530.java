	public ActionSetDescriptor(IConfigurationElement configElement) throws CoreException {
		super();
		this.configElement = configElement;
		id = configElement.getAttribute(IWorkbenchRegistryConstants.ATT_ID);
		pluginId = configElement.getNamespace();
		label = configElement.getAttribute(IWorkbenchRegistryConstants.ATT_LABEL);
		description = configElement.getAttribute(IWorkbenchRegistryConstants.TAG_DESCRIPTION);
		String str = configElement.getAttribute(IWorkbenchRegistryConstants.ATT_VISIBLE);
		if (str != null && str.equals("true")) { //$NON-NLS-1$
