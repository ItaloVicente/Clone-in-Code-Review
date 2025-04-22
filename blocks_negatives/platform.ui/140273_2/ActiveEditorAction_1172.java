        super.partDeactivated(part);
        if (part instanceof IEditorPart) {
            updateActiveEditor();
            updateState();
        }
    }

    /**
     * Set the active editor
     */
    private void setActiveEditor(IEditorPart part) {
        if (activeEditor == part) {
            return;
        }
        if (activeEditor != null) {
            editorDeactivated(activeEditor);
        }
        activeEditor = part;
        if (activeEditor != null) {
            editorActivated(activeEditor);
        }
    }

    /**
     * Update the active editor based on the current
     * active page.
     */
    private void updateActiveEditor() {
        if (getActivePage() == null) {
            setActiveEditor(null);
        } else {
            setActiveEditor(getActivePage().getActiveEditor());
        }
    }

    /**
     * Update the state of the action. By default, the action
     * is enabled if there is an active editor.
     *
     * Subclasses may override or extend this method.
     */
    protected void updateState() {
        setEnabled(getActiveEditor() != null);
    }
