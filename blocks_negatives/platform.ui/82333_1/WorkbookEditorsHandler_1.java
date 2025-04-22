	public Object execute(ExecutionEvent event) throws ExecutionException {
		MUIElement uiElement = null;

		IWorkbenchPart activePart = HandlerUtil.getActivePart(event);
		if (activePart != null) {
			IWorkbenchWindow workbenchWindow = HandlerUtil.getActiveWorkbenchWindowChecked(event);
			WorkbenchPage page = (WorkbenchPage) workbenchWindow.getActivePage();
			if (page != null) {
				IWorkbenchPartReference reference = page.getReference(activePart);
				if (reference != null) {
					uiElement = page.getActiveElement(reference);
