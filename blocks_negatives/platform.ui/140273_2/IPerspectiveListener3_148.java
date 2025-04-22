    /**
     * Notifies this listener that a perspective in the given page has been
     * deactivated.
     *
     * @param page
     *            the page containing the deactivated perspective
     * @param perspective
     *            the perspective descriptor that was deactivated
     * @see IWorkbenchPage#setPerspective(IPerspectiveDescriptor)
     */
    void perspectiveDeactivated(IWorkbenchPage page,
            IPerspectiveDescriptor perspective);
