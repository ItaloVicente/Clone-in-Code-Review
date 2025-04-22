    /**
     * Convenience method.  Looks for a readme file in the selection,
     * and if one is found, returns the sections for it.  Returns null
     * if there is no readme file in the selection.
     */
    public AdaptableList getSections(ISelection sel) {
        if (!(sel instanceof IStructuredSelection))
            return null;
        IStructuredSelection structured = (IStructuredSelection) sel;
