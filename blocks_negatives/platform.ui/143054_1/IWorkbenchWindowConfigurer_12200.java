	 * This should only be called if the advisor is defining custom window
	 * contents in <code>createWindowContents</code>, and may only be called
	 * once. The caller must lay out the cool bar appropriately within the
	 * parent, but must not add, remove or change items in the result (hence the
	 * return type of <code>Control</code>). The cool bar is populated by the
	 * window's cool bar manager. The application can add to the cool bar
	 * manager in the advisor's <code>fillActionBars</code> method instead.
