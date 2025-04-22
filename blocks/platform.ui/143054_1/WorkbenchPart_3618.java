	@Override
	public void setInitializationData(IConfigurationElement cfig, String propertyName, Object data) {

		configElement = cfig;

		partName = Util.safeString(cfig.getAttribute("name"));//$NON-NLS-1$ ;
		title = partName;

		String strIcon = cfig.getAttribute("icon");//$NON-NLS-1$
		if (strIcon == null) {
