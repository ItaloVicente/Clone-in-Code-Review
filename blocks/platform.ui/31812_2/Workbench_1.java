
		IWorkbenchWindow activeWindow = getActiveWorkbenchWindow();
		if (activeWindow == null) {
			activeWindow = windows[0];
		}
		return WorkbenchPage.saveAll(new ArrayList<ISaveablePart>(dirtyParts),
				confirm, closing, true, activeWindow, activeWindow);
