    /**
     * Opens an editor for the given editor input and editor id combination on the
     * given workbench page in response to a drop on the workbench editor area.
     * In contrast to other ways of opening an editor, we never open an external
     * editor in this case (since external editors appear in their own window and
     * not in the editor area). The operation fails silently if the editor
     * cannot be opened.
     *
     * @param page the workbench page
     * @param editorInput the editor input
     * @param editorId the editor id
     * @return the editor part that was opened, or <code>null</code> if no editor
     * was opened
     */
    private IEditorPart openNonExternalEditor(IWorkbenchPage page,
            IEditorInput editorInput, String editorId) {
        IEditorPart result;
        try {
            IEditorRegistry editorReg = PlatformUI.getWorkbench()
                    .getEditorRegistry();
            IEditorDescriptor editorDesc = editorReg.findEditor(editorId);
            if (editorDesc != null && !editorDesc.isOpenExternal()) {
                result = page.openEditor(editorInput, editorId);
            } else {
                result = null;
            }
        } catch (PartInitException e) {
            result = null;
        }
        return result;
    }
