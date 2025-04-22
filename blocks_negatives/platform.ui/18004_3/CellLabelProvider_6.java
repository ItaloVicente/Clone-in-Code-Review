	/**
	 * Return whether or not to use the native tool tip. If you switch to native
	 * tool tips only the value from {@link #getToolTipText(Object)} is used all
	 * other features from custom tool tips are not supported.
	 * 
	 * <p>
	 * To reset the control to native behavior you should return
	 * <code>true</code> from this method and <code>null</code> from
	 * {@link #getToolTipText(Object)} or <code>null</code> from
	 * {@link #getToolTipText(Object)} and {@link #getToolTipImage(Object)} at
	 * the same time
	 * </p>
	 * 
	 * @param object
	 *            the {@link Object} for which the tool tip is shown
	 * @return <code>true</code> if native tool tips should be used
	 */
