	/**
	 * Removes the given element from the viewer. The selection is updated if
	 * necessary.
	 * <p>
	 * This method should be called (by the content provider) when a single
	 * element has been removed from the model, in order to cause the viewer to
	 * accurately reflect the model. This method only affects the viewer, not
	 * the model. Note that there is another method for efficiently processing
	 * the simultaneous removal of multiple elements.
	 * </p>
	 *
	 * @param elementsOrTreePaths
	 *            the element
	 */
	public void remove(Object elementsOrTreePaths) {
		remove(new Object[] { elementsOrTreePaths });
	}

