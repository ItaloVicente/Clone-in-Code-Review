		IWorkbenchWindow[] windows = getWorkbenchWindows();
		if (windows.length == 0) {
			return true;
		}

		Set<ISaveablePart> dirtyParts = new HashSet<ISaveablePart>();
		for (IWorkbenchWindow window : windows) {
			WorkbenchPage page = (WorkbenchPage) window.getActivePage();
