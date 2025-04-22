        if (source instanceof IEditorPart) {
            if (propID == IEditorPart.PROP_DIRTY) {
                updateState();
            }
        }
    }
