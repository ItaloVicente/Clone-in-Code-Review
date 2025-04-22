		if (activeWindow != null) {
			return activeWindow.getShell();
		} else if (workbench.getWorkbenchWindowCount() > 0) {
			return workbench.getWorkbenchWindows()[0].getShell();
		}
		return null;
