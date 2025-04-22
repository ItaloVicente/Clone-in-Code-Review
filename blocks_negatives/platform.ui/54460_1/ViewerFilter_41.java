	/**
	 * Returns whether the given element makes it through this filter.
	 *
	 * @param viewer
	 *            the viewer
	 * @param parentElement
	 *            the parent element
	 * @param element
	 *            the element
	 * @return <code>true</code> if element is included in the filtered set, and
	 *         <code>false</code> if excluded
	 */
	public abstract boolean select(Viewer viewer, Object parentElement,
			Object element);
