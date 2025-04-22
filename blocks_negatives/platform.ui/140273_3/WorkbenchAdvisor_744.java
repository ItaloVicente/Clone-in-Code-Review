	 * window. It is not called when the window is being closed for other
	 * reasons. Clients must not call this method directly (although super calls
	 * are okay). The default implementation does nothing. Subclasses may
	 * override. Typical clients may use the configurer passed in to access the
	 * workbench window being closed. If this method returns <code>false</code>,
	 * then the user's request to close the shell is ignored. This gives the
	 * workbench advisor an opportunity to query the user and/or veto the
	 * closing of a window under some circumstances.
