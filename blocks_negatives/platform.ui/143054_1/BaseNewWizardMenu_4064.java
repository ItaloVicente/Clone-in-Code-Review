    /**
     * Returns whether the new wizards registry has a non-empty category with
     * the given identifier.
     *
     * @param categoryId
     *            the identifier for the category
     * @return <code>true</code> if there is a non-empty category with the
     *         given identifier, <code>false</code> otherwise
     */
    protected boolean registryHasCategory(String categoryId) {
    	return WorkbenchPlugin.getDefault().getNewWizardRegistry()
				.findCategory(categoryId) != null;
    }
