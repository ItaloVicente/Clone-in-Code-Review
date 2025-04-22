    /**
     * Opens an editor for the given marker on the given workbench page in response
     * to a drop on the workbench editor area. In contrast to other ways of opening
     * an editor, we never open an external editor in this case (since external
     * editors appear in their own window and not in the editor area).
     * The operation fails silently if there is no suitable editor to open.
     *
     * @param page the workbench page
     * @param marker the marker to open
     * @return the editor part that was opened, or <code>null</code> if no editor
     * was opened
     */
    private IEditorPart openNonExternalEditor(IWorkbenchPage page,
            IMarker marker) {
        IEditorPart result;
        try {
            if (!(marker.getResource() instanceof IFile)) {
                return null;
            }
            IFile file = (IFile) marker.getResource();
