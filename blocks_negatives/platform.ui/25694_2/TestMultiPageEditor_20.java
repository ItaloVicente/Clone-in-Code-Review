    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.part.MultiPageEditorPart#createPages()
     */
    protected void createPages() {
        try {
            IEditorPart part1 = new TestKeyBindingMultiPageEditorPart(0);
            addPage(part1, getEditorInput());
