    /**
	 * Utility method that will add the <code>element</code> to each given
	 * working set in <code>workingSets</code> if possible. This method will
	 * invoke {@link IWorkingSet#adaptElements(IAdaptable[])} for the element on
	 * each working set and the result of this method will be used rather than
	 * the original element in the addition operation.
	 *
	 * @param element
	 *            the element to adapt and then add to the working sets
	 * @param workingSets
	 *            the working sets to add the element to
