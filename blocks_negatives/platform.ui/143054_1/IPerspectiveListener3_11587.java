    /**
     * Notifies this listener that a perspective in the given page has been
     * closed.
     *
     * @param page
     *            the page containing the closed perspective
     * @param perspective
     *            the perspective descriptor that was closed
     * @see IWorkbenchPage#closePerspective(IPerspectiveDescriptor, boolean, boolean)
     * @see IWorkbenchPage#closeAllPerspectives(boolean, boolean)
     */
    public void perspectiveClosed(IWorkbenchPage page,
            IPerspectiveDescriptor perspective);
