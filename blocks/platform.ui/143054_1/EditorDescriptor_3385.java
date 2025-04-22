		if (program == null) {
			if (configurationElement == null) {
				return editorName;
			}
			return configurationElement.getAttribute(IWorkbenchRegistryConstants.ATT_NAME);
		}
		return program.getName();
	}

	public String getLauncher() {
		if (configurationElement == null) {
