    /**
     * Tests that the <code>bringToTop(IWorkbenchPart)</code> correctly
     * updates the activation list.
     *
     * @throws CoreException
     *             If the test project cannot be created or opened.
     */
    public void testBringToTop() throws CoreException {
        IWorkbenchWindow window = openTestWindow();
        IWorkspace workspace = ResourcesPlugin.getWorkspace();
