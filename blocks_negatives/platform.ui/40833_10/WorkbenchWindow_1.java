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
