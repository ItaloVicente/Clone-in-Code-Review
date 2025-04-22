    /**
     * Tests the session view within a page.
     */
    private void testSessionView(IWorkbenchPage page) {
        IViewPart view = page.findView(SessionView.VIEW_ID);
        assertNotNull(view);
        SessionView sessionView = (SessionView) view;
