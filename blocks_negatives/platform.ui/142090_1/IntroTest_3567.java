    public void testStandby() {
        IWorkbench workbench = window.getWorkbench();
        IIntroPart part = workbench.getIntroManager().showIntro(window, false);
        assertNotNull(part);
        assertFalse(workbench.getIntroManager().isIntroStandby(part));
        workbench.getIntroManager().setIntroStandby(part, true);
        assertTrue(workbench.getIntroManager().isIntroStandby(part));
        assertTrue(workbench.getIntroManager().closeIntro(part));
        assertNull(workbench.getIntroManager().getIntro());
    }

    /**
     * Open the intro, change perspective, close the intro (ensure it still
     * exists), change back to the first perspective, close the intro, ensure
     * that it no longer exists.
     */
    public void testPerspectiveChange() {
