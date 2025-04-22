        super.partClosed(part);
        if (part instanceof IEditorPart) {
            part.removePropertyListener(this);
            partsWithListeners.remove(part);
            updateState();
        }
    }
