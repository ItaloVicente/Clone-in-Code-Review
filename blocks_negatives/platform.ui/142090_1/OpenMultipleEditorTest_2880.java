    /**
     * @param testName
     */
    public OpenMultipleEditorTest(String extension, boolean closeAll, int tagging) {
        super ("testOpenMultipleEditors:" + extension + (closeAll ? "[closeAll]" : "[closeEach]"), tagging);
        this.extension = extension;
        this.closeAll = closeAll;
    }
