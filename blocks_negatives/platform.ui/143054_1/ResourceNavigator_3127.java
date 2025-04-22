        if (getActionGroup() != null) {
            getActionGroup().dispose();
        }
        Control control = viewer.getControl();
        if (dragDetectListener != null && control != null
                && control.isDisposed() == false) {
            control.removeListener(SWT.DragDetect, dragDetectListener);
        }

        super.dispose();
    }

    /**
     * An editor has been activated.  Sets the selection in this navigator
     * to be the editor's input, if linking is enabled.
     *
     * @param editor the active editor
     * @since 2.0
     */
    protected void editorActivated(IEditorPart editor) {
        if (!isLinkingEnabled()) {
            return;
        }

        IFile file = ResourceUtil.getFile(editor.getEditorInput());
        if (file != null) {
            ISelection newSelection = new StructuredSelection(file);
