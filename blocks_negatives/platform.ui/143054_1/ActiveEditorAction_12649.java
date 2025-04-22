        super.partClosed(part);
        if (part instanceof IEditorPart) {
            updateActiveEditor();
            updateState();
        }
    }

    @Override
