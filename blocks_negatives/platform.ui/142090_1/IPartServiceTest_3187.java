    /**
     * Tests that when a partOpened is received for an editor being opened,
     * the editor is available via findEditor, getEditors, and getEditorReferences.
     *
     * @since 3.1
     */
    public void testEditorFoundWhenOpened() throws Throwable {
    	final String editorId = MockEditorPart.ID1;
