        if (dialog.open() == Window.OK) {
            String name = dialog.getName();
            String extension = dialog.getExtension();
            addResourceType(name, extension);
        }
    }

    /**
     * Remove the editor from the table
     */
    public void removeSelectedEditor() {
        TableItem[] items = editorTable.getSelection();
        boolean defaultEditor = editorTable.getSelectionIndex() == 0;
        if (items.length > 0) {
        	for (TableItem item : items) {
                getSelectedResourceType().removeEditor(
                        (EditorDescriptor) item.getData(DATA_EDITOR));
                item.dispose();
        	}
        }
        if (defaultEditor && editorTable.getItemCount() > 0) {
            TableItem item = editorTable.getItem(0);
            getSelectedResourceType().setDefaultEditor(
					(EditorDescriptor) item.getData(DATA_EDITOR));
			item.setText(((EditorDescriptor) (item.getData(DATA_EDITOR))).getLabel()
