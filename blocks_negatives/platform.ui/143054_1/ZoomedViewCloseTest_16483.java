    /**
     * <p>Test: Activate an unstacked view, activate a stacked part, then close the active part.</p>
     * <p>Expected result: The unstacked part becomes active</p>
     * <p>Note: This isn't really a zoom test, but it ensures that activation
     *    will move between stacks when there is no zoom.</p>
     */
    public void testCloseUnzoomedStackedViewAfterActivatingView() {
        IWorkbenchPart activePart = getStackedPart1();
        IWorkbenchPart unstackedPart = unstackedView;
