    /**
     * Returns true if any of the elements makes it through the filter.
     * This method uses caching if enabled; the computation is done in
     * computeAnyVisible.
     *
     * @param viewer
     * @param parent
     * @param elements the elements (must not be an empty array)
     * @return true if any of the elements makes it through the filter.
     */
    private boolean isAnyVisible(Viewer viewer, Object parent, Object[] elements) {
    	if (matcher == null) {
    		return true;
    	}
