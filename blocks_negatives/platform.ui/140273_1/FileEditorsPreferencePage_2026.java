    protected Button addResourceTypeButton;

    protected Button removeResourceTypeButton;

    protected Table editorTable;

    protected Button addEditorButton;

    protected Button removeEditorButton;

    protected Button defaultEditorButton;

    protected Label editorLabel;

    protected IWorkbench workbench;

    protected List imagesToDispose;

    protected Map editorsToImages;

    /**
     * Add a new resource type to the collection shown in the top of the page.
     * This is typically called after the extension dialog is shown to the user.
     *
     * @param newName the new name
     * @param newExtension the new extension
     */
    public void addResourceType(String newName, String newExtension) {
        Assert.isTrue((newName != null && newName.length() != 0)
                || (newExtension != null && newExtension.length() != 0));

        int index = newName.indexOf('*');
        if (index > -1) {
            Assert.isTrue(index == 0 && newName.length() == 1);
            Assert.isTrue(newExtension != null && newExtension.length() != 0);
        }

        String newFilename = (newName + (newExtension == null
        IFileEditorMapping resourceType;
        TableItem[] items = resourceTypeTable.getItems();
        boolean found = false;
        int i = 0;

        while (i < items.length && !found) {
            resourceType = (IFileEditorMapping) items[i].getData();
            int result = newFilename.compareToIgnoreCase(resourceType
                    .getLabel());
            if (result == 0) {
                MessageDialog
                        .openInformation(
                                getControl().getShell(),
                                WorkbenchMessages.FileEditorPreference_existsTitle,
                                WorkbenchMessages.FileEditorPreference_existsMessage);
                return;
            }

            if (result < 0) {
