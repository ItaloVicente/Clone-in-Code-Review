	private void handlePostSelectionChanged(MPart part, Object selection, boolean targeted) {
		selection = createCompatibilitySelection(selection);

		Object client = part.getObject();
		if (client instanceof CompatibilityPart) {
			IWorkbenchPart workbenchPart = ((CompatibilityPart) client).getPart();
			if (targeted) {
				notifyListeners(workbenchPart, (ISelection) selection, part.getElementId(),
						targetedPostSelectionListeners);
			} else {
				notifyListeners(workbenchPart, (ISelection) selection, postSelectionListeners);
