        IEditorPart oldEditor = getActiveEditor();
        super.activateEditor(part);
        updateGradient(oldEditor);
    }

    /**
     * Return true if the shell is activated.
     */
    protected boolean getShellActivated() {
        WorkbenchWindow window = (WorkbenchWindow) getSite().getPage()
                .getWorkbenchWindow();
        return window.getShellActivated();
    }
