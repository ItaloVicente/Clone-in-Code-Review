    private ResourceUtil() {
    }

    /**
     * Returns the file corresponding to the given editor input, or <code>null</code>
     * if there is no applicable file.
     * Returns <code>null</code> if the given editor input is <code>null</code>.
     *
     * @param editorInput the editor input, or <code>null</code>
     * @return the file corresponding to the editor input, or <code>null</code>
     */
    public static IFile getFile(IEditorInput editorInput) {
		return Adapters.adapt(editorInput, IFile.class);
    }

    /**
     * Returns the resource corresponding to the given editor input, or <code>null</code>
     * if there is no applicable resource.
     * Returns <code>null</code> if the given editor input is <code>null</code>.
     *
     * @param editorInput the editor input
     * @return the file corresponding to the editor input, or <code>null</code>
     */
    public static IResource getResource(IEditorInput editorInput) {
