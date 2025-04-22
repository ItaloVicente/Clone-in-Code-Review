    /**
     * The multi-page editor.
     */
    private MultiPageEditorPart multiPageEditor;

    /**
     * Creates a selection provider for the given multi-page editor.
     *
     * @param multiPageEditor the multi-page editor
     */
    public MultiPageSelectionProvider(MultiPageEditorPart multiPageEditor) {
        Assert.isNotNull(multiPageEditor);
        this.multiPageEditor = multiPageEditor;
    }

    @Override
