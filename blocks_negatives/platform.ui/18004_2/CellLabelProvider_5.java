	/**
	 * Return the amount of pixels in x and y direction you want the tool tip to
	 * pop up from the mouse pointer. The default shift is 10px right and 0px
	 * below your mouse cursor. Be aware of the fact that you should at least
	 * position the tool tip 1px right to your mouse cursor else click events
	 * may not get propagated properly.
	 * 
	 * @param object
	 *            the element for which the tool tip is shown
	 * @return {@link Point} to shift of the tool tip or <code>null</code> if the
	 *         default shift should be used.
	 */
