        return resourcesToExport;
    }

    /**
     * Returns this page's collection of currently-specified resources to be
     * exported. This is the primary resource selection facility accessor for
     * subclasses.
     *
     * @return an iterator over the collection of resources currently selected
     * for export (element type: <code>IResource</code>). This will include
     * white checked folders and individually checked files.
     */
    protected Iterator getSelectedResourcesIterator() {
        return this.resourceGroup.getAllCheckedListItems().iterator();
    }

    /**
     * Returns the resource extensions currently specified to be exported.
     *
     * @return the resource extensions currently specified to be exported (element
     *   type: <code>String</code>)
     */
    protected List getTypesToExport() {

        return selectedTypes;
    }

    /**
     * Returns this page's collection of currently-specified resources to be
     * exported. This returns both folders and files - for just the files use
     * getSelectedResources.
     *
     * @return a collection of resources currently selected
     * for export (element type: <code>IResource</code>)
     */
    protected List getWhiteCheckedResources() {

        return this.resourceGroup.getAllWhiteCheckedItems();
    }

    /**
     * Queries the user for the types of resources to be exported and selects
     * them in the checkbox group.
     */
    protected void handleTypesEditButtonPressed() {
        Object[] newSelectedTypes = queryResourceTypesToExport();

            this.selectedTypes = new ArrayList(newSelectedTypes.length);
            for (Object newSelectedType : newSelectedTypes) {
                this.selectedTypes.add(newSelectedType);
            }
            setupSelectionsBasedOnSelectedTypes();
        }

    }

    /**
     * Returns whether the extension of the given resource name is an extension that
     * has been specified for export by the user.
     *
     * @param resourceName the resource name
     * @return <code>true</code> if the resource name is suitable for export based
     *   upon its extension
     */
    protected boolean hasExportableExtension(String resourceName) {
        if (selectedTypes == null) {
