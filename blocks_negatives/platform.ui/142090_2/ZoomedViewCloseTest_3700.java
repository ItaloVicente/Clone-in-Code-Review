    /**
     * <p>Test: Zoom a view, then close the active editor.</p>
     * <p>Expected result: The view remains zoomed and active. A new editor is selected as the
     *    active editor.</p>
     * <p>Note: The behavior of this test changed intentionally on 050416. Closing the active editor
     *    no longer unzooms if a view is zoomed.</p>
     */
    public void testCloseActiveEditorWhileViewZoomed() {
        page.activate(editor1);
        zoom(stackedView1);
        close(editor1);
