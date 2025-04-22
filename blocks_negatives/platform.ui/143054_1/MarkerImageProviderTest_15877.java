    /**
     * Tests the dynamic form of the extension, where an IMarkerImageProvider class is given.
     */
    public void testDynamic() {
        IWorkspace workspace = ResourcesPlugin.getWorkspace();
        IMarker marker = null;
        try {
            marker = workspace.getRoot().createMarker(
        } catch (CoreException e) {
            fail(e.getMessage());
        }
