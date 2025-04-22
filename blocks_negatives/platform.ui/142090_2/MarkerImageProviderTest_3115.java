    /**
     * Tests the static form of the extension, where just a file path is given.
     */
    public void testStatic() {
        IWorkspace workspace = ResourcesPlugin.getWorkspace();
        IMarker marker = null;
        try {
            marker = workspace.getRoot().createMarker(
        } catch (CoreException e) {
            fail(e.getMessage());
        }
