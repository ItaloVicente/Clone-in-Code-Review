		if (IWorkbenchPreferenceConstants.DOCK_PERSPECTIVE_BAR.equals(propertyName)) {
			IWorkbench workbench = PlatformUI.getWorkbench();
			for (IWorkbenchWindow window : workbench.getWorkbenchWindows()) {
				if (window instanceof WorkbenchWindow) {
				}
			}
			return;
		}

		if (IWorkbenchPreferenceConstants.SHOW_TRADITIONAL_STYLE_TABS.equals(propertyName)) {

			IWorkbench workbench = PlatformUI.getWorkbench();
			for (IWorkbenchWindow window : workbench.getWorkbenchWindows()) {
				if (window instanceof WorkbenchWindow) {
				}
			}
			return;
		}

