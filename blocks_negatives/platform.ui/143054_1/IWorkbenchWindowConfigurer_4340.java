    /**
	 * Creates the status line control.
	 * <p>
	 * This should only be called if the advisor is defining custom window
	 * contents in <code>createWindowContents</code>, and may only be called
	 * once. The caller must lay out the status line appropriately within the
	 * parent, but must not add, remove or change items in the result (hence the
	 * return type of <code>Control</code>). The status line is populated by the
	 * window's status line manager. The application can add to the status line
	 * manager in the advisor's <code>fillActionBars</code> method instead.
	 * </p>
