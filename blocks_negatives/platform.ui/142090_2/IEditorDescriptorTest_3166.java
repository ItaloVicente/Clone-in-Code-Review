        IFileEditorMapping mapping = (IFileEditorMapping) ArrayUtil
                .pickRandom(PlatformUI.getWorkbench().getEditorRegistry()
                        .getFileEditorMappings());
        fEditors = mapping.getEditors();
    }

    public void testGetId() throws Throwable {
        for (IEditorDescriptor fEditor : fEditors) {
