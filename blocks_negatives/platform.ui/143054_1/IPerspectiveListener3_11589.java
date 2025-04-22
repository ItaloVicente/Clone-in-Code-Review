    /**
     * Notifies this listener that a perspective in the given page has been
     * saved as a new perspective with a different perspective descriptor.
     *
     * @param page
     *            the page containing the saved perspective
     * @param oldPerspective
     *            the old perspective descriptor
     * @param newPerspective
     *            the new perspective descriptor
     * @see IWorkbenchPage#savePerspectiveAs(IPerspectiveDescriptor)
     */
    public void perspectiveSavedAs(IWorkbenchPage page,
            IPerspectiveDescriptor oldPerspective,
            IPerspectiveDescriptor newPerspective);
