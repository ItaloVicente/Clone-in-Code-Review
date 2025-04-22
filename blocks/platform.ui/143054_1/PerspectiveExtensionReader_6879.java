		return true;
	}

	private boolean processHiddenMenuItem(IConfigurationElement element) {
		String id = element.getAttribute(IWorkbenchRegistryConstants.ATT_ID);
		if (id != null) {
