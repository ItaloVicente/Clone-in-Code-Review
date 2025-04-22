     *
     * @return the <code>ResourceComparator</code>
     * @since 3.3
     */

    @Override
	public ResourceComparator getComparator(){
    	ViewerComparator comparator = getTreeViewer().getComparator();
    	if (comparator instanceof ResourceComparator) {
    		return (ResourceComparator) comparator;
    	}
    	return null;
    }
    /**
     * Returns the resource viewer which shows the resource hierarchy.
     * @since 2.0
     */
    @Override
