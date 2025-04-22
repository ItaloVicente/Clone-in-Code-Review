	/**
	 * Returns whether this viewer filter would be affected by a change to the
	 * given property of the given element.
	 * <p>
	 * The default implementation of this method returns <code>false</code>.
	 * Subclasses should reimplement.
	 * </p>
	 *
	 * @param element
	 *            the element
	 * @param property
	 *            the property
	 * @return <code>true</code> if the filtering would be affected, and
	 *         <code>false</code> if it would be unaffected
	 */
	public boolean isFilterProperty(Object element, String property) {
		return false;
	}

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
