        IEditorInput input = editor.getEditorInput();
        if (input instanceof IFileEditorInput) {
            IFileEditorInput fileInput = (IFileEditorInput) input;
            IFile file = fileInput.getFile();
            ISelection newSelection = new StructuredSelection(file);
            if (!viewer.getSelection().equals(newSelection)) {
                viewer.setSelection(newSelection);
            }
        }

    }

    /**
     * Called when the context menu is about to open.
     */
    void fillContextMenu(IMenuManager menu) {
        actionGroup.setContext(new ActionContext(getViewer().getSelection()));
        actionGroup.fillContextMenu(menu);
    }

    /**
     * Returns the initial input for the viewer.
     * Tries to convert the input to a resource, either directly or via IAdaptable.
     * If the resource is a container, it uses that.
     * If the resource is a file, it uses its parent folder.
     * If a resource could not be obtained, it uses the workspace root.
     */
    IContainer getInitialInput() {
        IAdaptable input = getSite().getPage().getInput();
