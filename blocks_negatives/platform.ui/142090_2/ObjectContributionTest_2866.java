    /**
     * This tests adaptable contributions that are not IResource.
     *
     * @since 3.1
     */
    public final void testAdaptables()  {
    	assertPopupMenus("1",
    			new String[] {"ICommon.1"},
    			new StructuredSelection(new Object[] {
    					new ObjectContributionClasses.A()}),
