	 * This should only be called if the advisor is defining custom window
	 * contents in <code>createWindowContents</code>, and may only be called
	 * once. The caller must set it in the shell using
	 * <code>Shell.setMenuBar(Menu)</code> but must not make add, remove or
	 * change items in the result. The menu bar is populated by the window's
	 * menu manager. The application can add to the menu manager in the
	 * advisor's <code>fillActionBars</code> method instead.
