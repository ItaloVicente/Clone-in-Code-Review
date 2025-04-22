	static String getPath(IConfigurationElement element) {
		return element.getAttribute(IWorkbenchRegistryConstants.ATT_PATH);
	}

	static String getMenuBarPath(IConfigurationElement element) {
		return element.getAttribute(IWorkbenchRegistryConstants.ATT_MENUBAR_PATH);
	}

	static String getToolBarPath(IConfigurationElement element) {
		return element.getAttribute(IWorkbenchRegistryConstants.ATT_TOOLBAR_PATH);
	}

