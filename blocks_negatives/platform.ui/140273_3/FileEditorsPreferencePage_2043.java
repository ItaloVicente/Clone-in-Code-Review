            EditorDescriptor editor = (EditorDescriptor) items[0].getData(DATA_EDITOR);
            getSelectedResourceType().setDefaultEditor(editor);
            IContentType fromContentType = (IContentType) items[0].getData(DATA_FROM_CONTENT_TYPE);
            TableItem item = new TableItem(editorTable, SWT.NULL, 0);
            item.setData(DATA_EDITOR, editor);
            if (fromContentType != null) {
