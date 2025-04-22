	 * Tell the workbench window a visible state for the fastview bar. This is
	 * only applicable if the window configurer also wishes the fast view bar to
	 * be visible.
	 * 
	 * @param visible
	 *            <code>true</code> or <code>false</code>
	 * @since 3.2
	 */
	public void setFastViewBarVisible(boolean visible) {
		boolean oldValue = fastViewBarVisible;
		fastViewBarVisible = visible;
		if (oldValue != fastViewBarVisible) {

		}
	}

	/**
	 * The workbench window take on the fastview bar. This is only applicable if
	 * the window configurer also wishes the fast view bar to be visible.
	 * 
	 * @return <code>true</code> if the workbench window thinks the fastview bar
	 *         should be visible.
	 * @since 3.2
	 */
	public boolean getFastViewBarVisible() {
		return fastViewBarVisible;
	}
