        viewer.getControl().setFocus();
    }

    /**
     * Sets the given property source provider as
     * the property source provider.
     * <p>
     * Calling this method is only valid if you are using
     * this page's default root entry.
     * </p>
     * @param newProvider the property source provider
     */
    public void setPropertySourceProvider(IPropertySourceProvider newProvider) {
        provider = newProvider;
        if (rootEntry instanceof PropertySheetEntry) {
            ((PropertySheetEntry) rootEntry)
                    .setPropertySourceProvider(provider);
            viewer.setRootEntry(rootEntry);
        }
    }

    /**
     * Sets the given entry as the model for the page.
     *
     * @param entry the root entry
     */
    public void setRootEntry(IPropertySheetEntry entry) {
        rootEntry = entry;
        if (viewer != null) {
