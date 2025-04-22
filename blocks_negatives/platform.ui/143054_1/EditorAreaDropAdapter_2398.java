    /**
     * Opens an editor for the given file on the given workbench page in response
     * to a drop on the workbench editor area. In contrast to other ways of opening
     * an editor, we never open an external editor in this case (since external
     * editors appear in their own window and not in the editor area).
     * The operation fails silently if there is no suitable editor to open.
     *
     * @param page the workbench page
     * @param file the file to open
     * @return the editor part that was opened, or <code>null</code> if no editor
     * was opened
     */
    private IEditorPart openNonExternalEditor(IWorkbenchPage page, IFile file) {
        IEditorPart result;
        try {
            IEditorDescriptor defaultEditorDesc = IDE.getDefaultEditor(file);
            if (defaultEditorDesc != null
                    && !defaultEditorDesc.isOpenExternal()) {
                result = IDE.openEditor(page, file, true);
            } else {
                IEditorRegistry editorReg = PlatformUI.getWorkbench()
                        .getEditorRegistry();
                IEditorDescriptor editorDesc = null;
                if (editorReg.isSystemInPlaceEditorAvailable(file.getName())) {
                    editorDesc = editorReg
                            .findEditor(IEditorRegistry.SYSTEM_INPLACE_EDITOR_ID);
                }
