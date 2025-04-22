    /**
     * Notifies this listener that a part in the given page's perspective
     * has changed in some way (for example, view show/hide, editor open/close, etc).
     *
     * @param page the workbench page containing the perspective
     * @param perspective the descriptor for the changed perspective
     * @param partRef the reference to the affected part
     * @param changeId one of the <code>CHANGE_*</code> constants on IWorkbenchPage
     */
    public void perspectiveChanged(IWorkbenchPage page,
            IPerspectiveDescriptor perspective,
            IWorkbenchPartReference partRef, String changeId);
