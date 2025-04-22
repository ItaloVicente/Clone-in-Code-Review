	/**
	 * Return the MWindow containing this element (if any). This may either be a 'top level' window
	 * -or- a detached window. This allows the min.max code to only affect elements in the window
	 * containing the element.
	 *
	 * @param element
	 *            The element to check
	 *
	 * @return the window containing the element.
	 */
	private MWindow getWindowFor(MUIElement element) {
		MUIElement parent = element.getParent();

		while (parent != null && !(parent instanceof MWindow))
			parent = parent.getParent();
