	/**
	 * Adds the given child element to this viewer as a child of the given
	 * parent element. If this viewer does not have a sorter, the element is
	 * added at the end of the parent's list of children; otherwise, the element
	 * is inserted at the appropriate position.
	 * <p>
	 * This method should be called (by the content provider) when a single
	 * element has been added to the model, in order to cause the viewer to
	 * accurately reflect the model. This method only affects the viewer, not
	 * the model. Note that there is another method for efficiently processing
	 * the simultaneous addition of multiple elements.
	 * </p>
	 *
	 * @param parentElementOrTreePath
	 *            the parent element or path
	 * @param childElement
	 *            the child element
	 */
	public void add(Object parentElementOrTreePath, Object childElement) {
		add(parentElementOrTreePath, new Object[] { childElement });
	}

