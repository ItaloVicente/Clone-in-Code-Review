    /**
     * <p>Test: Activate an unstacked editor, activate an unstacked view, activate a stacked editor,
     *    then close the active editor.</p>
     * <p>Expected result: The previously active editor becomes active (even though a view is next
     *    in the activation list)</p>
     * <p>Note: This isn't really a zoom test, but it ensures that activation doesn't move from an editor
     *    to a view when the active editor is closed. Activating an editor in a different stack first
     *    ensures that activation WILL move between editor stacks to follow the activation order.</p>
     */
    public void testCloseUnzoomedStackedEditorAfterActivatingView() {
        page.activate(editor3);
        page.activate(unstackedView);
        page.activate(editor1);
        close(editor1);
