        return getLabel();
    }

    /**
     * Return the id for this category.
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * Return the label for this category.
     *
     * @return the label
     */
    public String getLabel() {
        return configurationElement == null ? name : configurationElement
				.getAttribute(IWorkbenchRegistryConstants.ATT_NAME);
    }

    /**
     * Return the parent path for this category.
     *
     * @return the parent path
     */
    public String[] getParentPath() {
    	if (parentPath != null) {
