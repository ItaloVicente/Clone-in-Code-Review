    /**
     * Constructs a NavigationLocation with its editor part.
     *
     * @param editorPart
     */
    protected NavigationLocation(IEditorPart editorPart) {
        this.page = editorPart.getSite().getPage();
        this.input = editorPart.getEditorInput();
    }
