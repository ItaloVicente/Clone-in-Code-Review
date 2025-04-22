        if (program == null) {
        	if (configurationElement == null) {
        		return editorName;
        	}
        	return configurationElement.getAttribute(IWorkbenchRegistryConstants.ATT_NAME);
        }
        return program.getName();
    }

    /**
     * Returns the class name of the launcher.
     *
     * @return the launcher class name
     */
    public String getLauncher() {
    	if (configurationElement == null) {
