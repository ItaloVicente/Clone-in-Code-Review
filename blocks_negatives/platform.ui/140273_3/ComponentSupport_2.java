    /**
     * Returns whether an in-place editor is available to
     * edit the file.
     *
     * @param filename the file name in the system
     */
    public static boolean inPlaceEditorAvailable(String filename) {
        if (inPlaceEditorSupported()) {
            return testForOleEditor(filename);
        }
