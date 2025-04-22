	 * Sets this viewer's comparator to be used for sorting elements, and triggers refiltering and 
	 * resorting of this viewer's element.  <code>null</code> turns sorting off.
	 * To get the viewer's comparator, call <code>getComparator()</code>.
     * <p>
     * IMPORTANT: This method was introduced in 3.2. If a reference to this viewer object 
     * is passed to clients who call <code>getSorter()</code>, null may be returned from
     * from that method even though the viewer is sorting its elements using the
     * viewer's comparator.
     * </p>
