    /**
     * Notifies this listener that a perspective has changed in some way
     * (for example, editor area hidden, perspective reset,
     * view show/hide, editor open/close, etc).
     *
     * @param page the page containing the affected perspective
     * @param perspective the perspective descriptor
     * @param changeId one of the <code>CHANGE_*</code> constants on IWorkbenchPage
     */
    void perspectiveChanged(IWorkbenchPage page,
            IPerspectiveDescriptor perspective, String changeId);
