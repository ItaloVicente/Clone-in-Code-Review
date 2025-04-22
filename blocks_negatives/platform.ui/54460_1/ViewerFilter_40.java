	public Object[] filter(Viewer viewer, TreePath parentPath, Object[] elements) {
		return filter(viewer, parentPath.getLastSegment(), elements);
	}

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
