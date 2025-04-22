        this.workbench = aWorkbench;
        noDefaultAndApplyButton();
    }

    /*
     * Create a new <code>TableItem</code> to represent the resource
     * type editor description supplied.
     */
    protected TableItem newResourceTableItem(IFileEditorMapping mapping,
            int index, boolean selected) {
        Image image = mapping.getImageDescriptor().createImage(false);
        if (image != null) {
