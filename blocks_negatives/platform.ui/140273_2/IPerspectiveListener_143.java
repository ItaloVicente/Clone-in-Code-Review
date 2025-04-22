    /**
     * Notifies this listener that a perspective in the given page
     * has been activated.
     *
     * @param page the page containing the activated perspective
     * @param perspective the perspective descriptor that was activated
     * @see IWorkbenchPage#setPerspective
     */
    void perspectiveActivated(IWorkbenchPage page,
            IPerspectiveDescriptor perspective);
