    /**
     * Notifies this listener that a perspective in the given page has been
     * opened.
     *
     * @param page
     *            the page containing the opened perspective
     * @param perspective
     *            the perspective descriptor that was opened
     * @see IWorkbenchPage#setPerspective(IPerspectiveDescriptor)
     */
    public void perspectiveOpened(IWorkbenchPage page,
            IPerspectiveDescriptor perspective);
