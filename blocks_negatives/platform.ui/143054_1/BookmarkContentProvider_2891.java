        IResource resource = (IResource) viewer.getInput();
        if (resource != null) {
            resource.getWorkspace().removeResourceChangeListener(this);
        }
    }

    /**
     * Returns all the bookmarks that should be shown for
     * the current settings.
     */
    Object[] getBookmarks(IResource resource) {
        try {
            return resource.findMarkers(IMarker.BOOKMARK, true,
                    IResource.DEPTH_INFINITE);
        } catch (CoreException e) {
            return new Object[0];
        }
    }

    public Object[] getChildren(Object element) {
        if (element instanceof IResource) {
