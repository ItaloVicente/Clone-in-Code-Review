	public static WorkbenchThemeManager getInstance() {
		if (instance == null) {
			if (PlatformUI.getWorkbench().getDisplay() != null) {
				PlatformUI.getWorkbench().getDisplay().syncExec(new Runnable() {
					@Override
					public void run() {
						getInternalInstance();
					}
				});
			}
		}
		return instance;
	}

	/**
	 * Initialize the singleton theme manager. Must be called in the UI thread.
	 * 
	 * @return the theme manager.
	 */
	private static synchronized WorkbenchThemeManager getInternalInstance() {
