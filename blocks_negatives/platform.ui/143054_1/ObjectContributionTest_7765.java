    /**
     * Test non-adaptable contributions
     *
     * @since 3.1
     */
    public final void testNonAdaptableContributions()  {
    	assertPopupMenus("1",
    			new String[] {"ICommon.2"},
    			new StructuredSelection(new Object[] {
    					new ObjectContributionClasses.A(),
