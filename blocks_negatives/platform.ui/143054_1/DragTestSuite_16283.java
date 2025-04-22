    }

    /**
     * Return all drop targets that apply to detached windows, given the window being dragged from.
     *
     * @param provider
     * @return
     * @since 3.1
     */
    private TestDropLocation[] getDetachedWindowDropTargets(IWorkbenchWindowProvider dragSource) {
        TestDropLocation[] targets = new TestDropLocation[] {
            new ViewDropTarget(dragSource, DragDropPerspectiveFactory.dropViewId1, SWT.CENTER),
            new ViewDropTarget(dragSource, DragDropPerspectiveFactory.dropViewId3, SWT.CENTER),
            new ViewTabDropTarget(dragSource, DragDropPerspectiveFactory.dropViewId1),
            new DetachedDropTarget()
        };
