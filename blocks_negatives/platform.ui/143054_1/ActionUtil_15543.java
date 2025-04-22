    /**
     * Runs the first action found in a window with a
     * particular label.
     *
     * @param test the current test case
     * @param win the containing window
     * @param label the action label
     */
    public static void runActionWithLabel(TestCase test, IWorkbenchWindow win,
            String label) {
        WorkbenchWindow realWin = (WorkbenchWindow) win;
        IMenuManager mgr = realWin.getMenuBarManager();
        runActionWithLabel(test, mgr, label);
    }
