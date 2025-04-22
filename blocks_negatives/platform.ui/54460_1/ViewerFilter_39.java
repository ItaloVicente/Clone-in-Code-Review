	/**
	 * Filters the given elements for the given viewer. The input array is not
	 * modified.
	 * <p>
	 * The default implementation of this method calls <code>select</code> on
	 * each element in the array, and returns only those elements for which
	 * <code>select</code> returns <code>true</code>.
	 * </p>
	 *
	 * @param viewer
	 *            the viewer
	 * @param parent
	 *            the parent element
	 * @param elements
	 *            the elements to filter
	 * @return the filtered elements
	 */
	public Object[] filter(Viewer viewer, Object parent, Object[] elements) {
		int size = elements.length;
		ArrayList<Object> out = new ArrayList<Object>(size);
		for (int i = 0; i < size; ++i) {
			Object element = elements[i];
			if (select(viewer, parent, element)) {
