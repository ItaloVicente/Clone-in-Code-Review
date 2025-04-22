        	item.dispose();
        }
        editorTable.removeAll();
    }

    /**
     * Add the selected editor to the default list.
     */
    public void setSelectedEditorAsDefault() {
        TableItem[] items = editorTable.getSelection();
        if (items.length > 0) {
            TableItem oldDefaultItem = editorTable.getItem(0);
            oldDefaultItem
                    .setText(((EditorDescriptor) oldDefaultItem.getData(DATA_EDITOR))
                            .getLabel());
            if (!isEditorRemovable(oldDefaultItem)) {
