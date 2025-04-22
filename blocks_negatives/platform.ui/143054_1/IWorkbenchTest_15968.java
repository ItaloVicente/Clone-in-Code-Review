        assertEquals(wins.length, oldTotal + num);

        closeAllTestWindows();
        wins = fWorkbench.getWorkbenchWindows();
        assertEquals(wins.length, oldTotal);
    }

    /**
     * openWorkbenchWindow(String, IAdaptable)
     */
    public void XXXtestOpenWorkbenchWindow() throws Throwable {
        IWorkbenchWindow win = null;
        try {
            win = fWorkbench.openWorkbenchWindow(EmptyPerspective.PERSP_ID, getPageInput());
            assertNotNull(win);
            assertEquals(win, fWorkbench.getActiveWorkbenchWindow());
            assertEquals(EmptyPerspective.PERSP_ID, win.getActivePage()
                    .getPerspective().getId());
        } finally {
            if (win != null) {
