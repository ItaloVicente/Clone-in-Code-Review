	 * Creates the page composite, in which the window's pages, and their views
	 * and editors, appear.
	 * <p>
	 * This should only be called if the advisor is defining custom window
	 * contents in <code>createWindowContents</code>, and may only be called
	 * once. The caller must lay out the page composite appropriately within the
	 * parent, but must not add, remove or change items in the result (hence the
	 * return type of <code>Control</code>). The page composite is populated by
	 * the workbench.
	 * </p>
