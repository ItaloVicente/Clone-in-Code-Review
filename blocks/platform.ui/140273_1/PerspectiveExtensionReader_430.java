		return true;
	}

	private boolean processHiddenToolBarItem(IConfigurationElement element) {
		String id = element.getAttribute(IWorkbenchRegistryConstants.ATT_ID);
		if (id != null) {
