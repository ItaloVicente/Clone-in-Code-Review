	 * containment in this working set. This is useful for UI elements which
	 * wish to filter contributions to working sets based on applicability. This
	 * is a hint, however, and is not considered when the
	 * {@link #setElements(IAdaptable[])} method is invoked.
	 *
	 * @param objects
	 *            the objects to transform
	 * @return an array of transformed elements that be empty if no elements
	 *         from the original array are suitable
