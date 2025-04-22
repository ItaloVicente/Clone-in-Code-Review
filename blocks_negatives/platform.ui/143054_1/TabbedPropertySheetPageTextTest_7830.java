    /**
     * When text is selected, there is one tab for each selected word.
     */
    public void test_tabForSelectedTextDisplay() {
        IDocument document = textTestsView.getViewer().getDocument();
        document.set("This is a test");
        textTestsView.getViewer().setSelectedRange(0, 14);
