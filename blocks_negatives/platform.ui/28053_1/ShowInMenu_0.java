		if (page != null) {
			IWorkbenchPart activePart = page.getActivePart();
			/*
			 * NOTE: Do not use window.getShell() to test since this won't work
			 * for detached views (see bug 412285)
			 */
			Shell activePartShell = activePart.getSite().getShell();
			if (activePartShell == activePartShell.getDisplay().getActiveShell())
				return activePart;
		}
		return null;
