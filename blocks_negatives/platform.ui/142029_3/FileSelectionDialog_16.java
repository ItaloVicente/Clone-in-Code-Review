        Composite composite = (Composite) super.createDialogArea(parent);

        createMessageArea(composite);

        FileSystemElement input = new FileSystemElement("", null, true);//$NON-NLS-1$
        input.addChild(root);
        root.setParent(input);

        selectionGroup = new CheckboxTreeAndListGroup(composite, input,
                getFolderProvider(), new WorkbenchLabelProvider(),
                getFileProvider(), new WorkbenchLabelProvider(), SWT.NONE,
                SIZING_SELECTION_WIDGET_WIDTH, // since this page has no other significantly-sized

        ICheckStateListener listener = event -> getOkButton().setEnabled(
		        selectionGroup.getCheckedElementCount() > 0);

        WorkbenchViewerComparator comparator = new WorkbenchViewerComparator();
        selectionGroup.setTreeComparator(comparator);
        selectionGroup.setListComparator(comparator);
        selectionGroup.addCheckStateListener(listener);

        addSelectionButtons(composite);

        return composite;
    }

    /**
     * Returns whether the tree view of the file system element
     * will be fully expanded when the dialog is opened.
     *
     * @return true to expand all on dialog open, false otherwise.
     */
    public boolean getExpandAllOnOpen() {
        return expandAllOnOpen;
    }

    /**
     * Returns a content provider for <code>FileSystemElement</code>s that returns
     * only files as children.
     */
    private ITreeContentProvider getFileProvider() {
        return new WorkbenchContentProvider() {
            @Override
