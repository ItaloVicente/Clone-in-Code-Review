    public void doubleClick(DoubleClickEvent event) {
        IStructuredSelection s = (IStructuredSelection) event.getSelection();
        Object element = s.getFirstElement();
        if (filteredTree.getViewer().isExpandable(element)) {
            filteredTree.getViewer().setExpandedState(element, !filteredTree.getViewer().getExpandedState(element));
        } else if (viewDescs.length > 0) {
            saveWidgetValues();
            setReturnCode(OK);
            close();
        }
    }

    /**
     * Return the dialog store to cache values into
     */
    protected IDialogSettings getDialogSettings() {
        IDialogSettings workbenchSettings = WorkbenchPlugin.getDefault()
                .getDialogSettings();
        IDialogSettings section = workbenchSettings
                .getSection(DIALOG_SETTING_SECTION_NAME);
        if (section == null) {
			section = workbenchSettings
                    .addNewSection(DIALOG_SETTING_SECTION_NAME);
