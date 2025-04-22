        for (int i = 0; i < children.size(); i++) {
            createItem(children.get(i), widget, i);
        }
    }

    /**
     * Creates a new cell editor listener.
     */
    private void createEditorListener() {
        editorListener = new ICellEditorListener() {
            @Override
