	@Override
	public void setInitializationData(IConfigurationElement cfig, String propertyName, Object data) {

		configElement = cfig;

		titleLabel = cfig.getAttribute(IWorkbenchRegistryConstants.ATT_LABEL);

		String strIcon = cfig.getAttribute(IWorkbenchRegistryConstants.ATT_ICON);
		if (strIcon == null) {
