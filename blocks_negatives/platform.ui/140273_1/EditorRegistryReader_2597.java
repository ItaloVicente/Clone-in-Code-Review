        IEditorDescriptor editor = getDefaultEditor();
        if (editor == null) {
            return WorkbenchImages
                    .getImageDescriptor(ISharedImages.IMG_OBJ_FILE);
        }
        return editor.getImageDescriptor();
    }

    @Override
