     * Determine if the editors list contains the editor descriptor.
     *
     * @param editors
     * 			The list of editors
     * @param editorDescriptor
     * 			The editor descriptor
     * @return <code>true</code> if the editors list contains the editor descriptor
     */
    private boolean contains(List<IEditorDescriptor> editors, IEditorDescriptor editorDescriptor) {
        for (IEditorDescriptor currentEditorDescriptor : editors) {
            if (currentEditorDescriptor.getId().equals(editorDescriptor.getId())) {
