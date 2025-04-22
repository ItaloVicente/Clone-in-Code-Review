		IEditorPart activeEditor = HandlerUtil.getActiveEditor(event);
		if (activeEditor != null) {
			IWorkbenchWindow workbenchWindow = HandlerUtil.getActiveWorkbenchWindowChecked(event);
			WorkbenchPage page = (WorkbenchPage) workbenchWindow.getActivePage();
			if (page != null) {
				IWorkbenchPartReference reference = page.getReference(activeEditor);
				if (reference != null) {
					uiElement = page.getActiveElement(reference);
