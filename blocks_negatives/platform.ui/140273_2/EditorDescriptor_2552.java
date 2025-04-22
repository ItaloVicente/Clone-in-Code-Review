    	return configurationElement.getAttribute(IWorkbenchRegistryConstants.ATT_LAUNCHER);
    }

    /**
     * Return the contributing plugin id.
     *
     * @return the contributing plugin id
     */
    public String getPluginID() {
    	if (configurationElement != null) {
