    /**
     * Return the input to the dialog.
     * @return IFileEditorMapping[]
     */
    private IFileEditorMapping[] getInput() {
        if (currentInput == null) {
            List wildcardEditors = new ArrayList();
            IFileEditorMapping[] allMappings = ((EditorRegistry)PlatformUI.getWorkbench()
                    .getEditorRegistry()).getUnifiedMappings();
            for (IFileEditorMapping allMapping : allMappings) {
