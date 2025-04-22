		return getLabel();
	}

	public String getId() {
		return id;
	}

	public String getLabel() {
		return configurationElement == null ? name
				: configurationElement.getAttribute(IWorkbenchRegistryConstants.ATT_NAME);
	}

	public String[] getParentPath() {
		if (parentPath != null) {
