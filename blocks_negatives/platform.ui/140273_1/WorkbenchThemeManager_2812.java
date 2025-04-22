	 * Initialize the WorkbenchThemeManager.
	 * Determine the default theme according to the following rules:
	 *   1) If we're in HC mode then default to system default
	 *   2) Otherwise, if preference already set (e.g. via plugin_customization.ini), then observe that value
	 *   3) Otherwise, use our default
	 * Call dispose when we close.
