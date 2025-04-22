        super.partOpened(part);
        if (part instanceof IEditorPart) {
            part.addPropertyListener(this);
            partsWithListeners.add(part);
            updateState();
        }
    }
