		if (page == null) {
			return null;
		}
		IWorkbenchPart activePart = page.getActivePart();
		if (activePart != null && activePart != this) {
			bootstrapSelection = page.getSelection();
			return activePart;
