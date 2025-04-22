    /**
     * Tests the state of a session editor.
     */
    private void testSessionEditor(IEditorPart part, String fileName) {
        IEditorSite site = part.getEditorSite();
        assertEquals(site.getId(), MockEditorPart.ID1);
        IEditorInput input = part.getEditorInput();
        assertTrue(input instanceof IFileEditorInput);
        IFile file = ((IFileEditorInput) input).getFile();
        assertEquals(fileName, file.getName());
    }
