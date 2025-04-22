        super.partBroughtToTop(part);
        if (part instanceof IEditorPart) {
            updateActiveEditor();
            updateState();
        }
    }

    @Override
