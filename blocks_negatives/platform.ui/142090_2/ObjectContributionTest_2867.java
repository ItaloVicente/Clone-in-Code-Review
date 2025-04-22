    /**
     * Ensure that there are no duplicate contributions.
     *
     * @since 3.1
     */
    public final void testDuplicateAdaptables() {
    	assertPopupMenus("1",
    			new String[] {"ICommon.1"},
    			new StructuredSelection(new Object[] {
    					new ObjectContributionClasses.D()}),
