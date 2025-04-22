    /** Shows the Navigator in a new test window. */
    protected void showNav() throws PartInitException {
        IWorkbenchWindow window = openTestWindow();
        navigator = (ResourceNavigator) window.getActivePage().showView(
                NAVIGATOR_VIEW_ID);
    }
