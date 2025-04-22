    /**
     * Returns the editor input for the editor referenced by this object.
     * <p>
     * Unlike most of the other methods on this type, this method
     * can trigger plug-in activation.
     * </p>
     *
     * @return the editor input for the editor referenced by this object
     * @throws PartInitException if there was an error restoring the editor input
     * @since 3.1
     */
    IEditorInput getEditorInput() throws PartInitException;
