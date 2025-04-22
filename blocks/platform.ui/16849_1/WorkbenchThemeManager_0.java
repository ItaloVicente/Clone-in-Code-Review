			if (PlatformUI.getWorkbench().getDisplay() != null) {
				PlatformUI.getWorkbench().getDisplay().syncExec(new Runnable() {
					public void run() {
						instance = new WorkbenchThemeManager();
						instance.getCurrentTheme(); // initialize the current
					}
				});
			}
