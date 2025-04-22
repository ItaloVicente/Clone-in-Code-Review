    /**
     * Open a test window with the empty perspective.
     */
    public IWorkbenchWindow openTestWindow() {
        return openTestWindow(EmptyPerspective.PERSP_ID);
    }

    /**
     * Open a test window with the provided perspective.
     */
    public IWorkbenchWindow openTestWindow(String perspectiveId) {
