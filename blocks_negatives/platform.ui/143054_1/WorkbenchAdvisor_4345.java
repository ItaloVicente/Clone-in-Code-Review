	 * This method is called before the window's controls have been created.
	 * Clients must not call this method directly (although super calls are
	 * okay). The default implementation does nothing. Subclasses may override.
	 * Typical clients will use the configurer passed in to tweak the workbench
	 * window in an application-specific way; however, filling the window's menu
	 * bar, tool bar, and status line must be done in
	 * {@link #fillActionBars fillActionBars}, which is called immediately
	 * after this method is called.
