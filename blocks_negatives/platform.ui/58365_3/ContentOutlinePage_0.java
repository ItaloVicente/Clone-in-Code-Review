	/**
	 * A hint for the styles to use while constructing the TreeViewer.
	 * <p>Subclasses may override.</p>
	 *
	 * @return the tree styles to use. By default, SWT.MULTI | SWT.H_SCROLL |
	 *         SWT.V_SCROLL
	 * @since 3.6
	 */
	protected int getTreeStyle() {
		return SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL;
