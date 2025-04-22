    /**
     * Runs an action identified by an id path in a
     * window.
     *
     * @param test the current test case
     * @param win the containing window
     * @param label the action label
     */
    public static void runActionUsingPath(TestCase test, IWorkbenchWindow win,
            String idPath) {
    	WorkbenchWindow realWin = (WorkbenchWindow) win;
        IMenuManager mgr = realWin.getMenuBarManager();
        runActionUsingPath(test, mgr, idPath);
    }
