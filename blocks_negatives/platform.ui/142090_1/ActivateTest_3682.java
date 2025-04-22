    /**
     * <p>Test: Zoom a pane, then reset perspective.</p>
     * <p>Expected result: the page unzooms but the original pane remains active</p>
     *
     * @since 3.1
     */
    public void testResetPerspective() {
        IWorkbenchPart zoomedPart = getStackedPart1();
