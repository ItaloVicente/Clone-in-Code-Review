	public static WorkbenchThemeManager getInstance() {
		if (instance == null) {
			if (PlatformUI.getWorkbench().getDisplay() != null) {
				PlatformUI.getWorkbench().getDisplay().syncExec(new Runnable() {
					public void run() {
						getInternalInstance();
					}
				});
			}
		}
		return instance;
	}

	private static synchronized WorkbenchThemeManager getInternalInstance() {
