    /**
     * <p>Test: Zoom an unstacked view and close it.</p>
     * <p>Expected result: The previously active part becomes active and unzoomed</p>
     * <p>Note: This ensures that the activation list is used if there is nothing available
     *    in the currently zoomed stack.</p>
     */
    public void testCloseZoomedUnstackedViewAfterActivatingView() {
        IWorkbenchPart previousActive = stackedView1;
        IWorkbenchPart zoomedPart = getUnstackedPart();
