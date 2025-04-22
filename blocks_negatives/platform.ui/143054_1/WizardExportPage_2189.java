        }

        return false;
    }

    /**
     * Persists additional setting that are to be restored in the next instance of
     * this page.
     * <p>
     * The <code>WizardImportPage</code> implementation of this method does
     * nothing. Subclasses may extend to persist additional settings.
     * </p>
     */
    protected void internalSaveWidgetValues() {
    }

    /**
     * Queries the user for the individual resources that are to be exported
     * and returns these resources as a collection.
     *
     * @param rootResource the resource to use as the root of the selection query
     * @return the resources selected for export (element type:
     *   <code>IResource</code>), or <code>null</code> if the user canceled the
     *   selection
     */
    protected Object[] queryIndividualResourcesToExport(IAdaptable rootResource) {
        ResourceSelectionDialog dialog = new ResourceSelectionDialog(
                getContainer().getShell(), rootResource, IDEWorkbenchMessages.WizardExportPage_selectResourcesTitle);
        dialog.setInitialSelections(selectedResources
                .toArray(new Object[selectedResources.size()]));
        dialog.open();
        return dialog.getResult();
    }

    /**
     * Queries the user for the resource types that are to be exported and returns
     * these types as a collection.
     *
     * @return the resource types selected for export (element type:
     *   <code>String</code>), or <code>null</code> if the user canceled the
     *   selection
     */
    protected Object[] queryResourceTypesToExport() {
        IFileEditorMapping editorMappings[] = PlatformUI.getWorkbench()
                .getEditorRegistry().getFileEditorMappings();

        int mappingsSize = editorMappings.length;
        List selectedTypes = getTypesToExport();
        List initialSelections = new ArrayList(selectedTypes.size());

        for (int i = 0; i < mappingsSize; i++) {
            IFileEditorMapping currentMapping = editorMappings[i];
            if (selectedTypes.contains(currentMapping.getExtension())) {
