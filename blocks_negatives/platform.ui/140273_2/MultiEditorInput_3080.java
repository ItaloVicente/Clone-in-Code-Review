    /**
     * Constructs a new MultiEditorInput.
     */
    public MultiEditorInput(String[] editorIDs, IEditorInput[] innerEditors) {
        Assert.isNotNull(editorIDs);
        Assert.isNotNull(innerEditors);
        editors = editorIDs;
        input = innerEditors;
    }
