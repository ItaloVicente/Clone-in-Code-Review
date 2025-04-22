	@Override
	public void setInitializationData(IConfigurationElement config, String propertyName, Object data)
			throws CoreException {
		if (data instanceof Hashtable) {
			Hashtable table = (Hashtable) data;
			fg = (String) table.get("foreground"); //$NON-NLS-1$
			bg1 = (String) table.get("background1"); //$NON-NLS-1$
			bg2 = (String) table.get("background2"); //$NON-NLS-1$
		}
