		return true;
	}

	private boolean processShowInPart(IConfigurationElement element) {
		String id = element.getAttribute(IWorkbenchRegistryConstants.ATT_ID);
		if (id != null) {
