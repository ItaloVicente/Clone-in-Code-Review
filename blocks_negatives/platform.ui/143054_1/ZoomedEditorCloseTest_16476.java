    /**
     * <p>Test: Activate a view, then zoom an unstacked editor and close it.</p>
     * <p>Expected result: The previously active Editor becomes active and unzoomed</p>
     * <p>Note: This ensures that the activation list is used if there is nothing available
     *    in the currently zoomed stack. It also ensures that activation never moves from
     *    an editor to a view when an editor is closed.</p>
     */
    public void testCloseZoomedUnstackedEditorAfterActivatingView() {
    	System.out.println("Bogus test: we don't unsoom in this case");
