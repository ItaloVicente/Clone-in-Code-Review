	 * Clients must not call this method directly (although super calls are
	 * okay). The default implementation opens the intro in the first window
	 * provided the preference IWorkbenchPreferences.SHOW_INTRO is
	 * <code>true</code>. If an intro is shown then this preference will be
	 * set to <code>false</code>. Subsequently, and intro will be shown only
	 * if <code>WorkbenchConfigurer.getSaveAndRestore()</code> returns
