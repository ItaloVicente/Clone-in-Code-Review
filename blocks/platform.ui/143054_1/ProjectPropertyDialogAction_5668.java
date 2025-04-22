	private IProject getProject() {
		IWorkbenchPart part = getActivePart();
		Object selection = null;
		if (part instanceof IEditorPart) {
			selection = ((IEditorPart) part).getEditorInput();
		} else {
			ISelection sel = workbenchWindow.getSelectionService()
					.getSelection();
			if ((sel != null) && (sel instanceof IStructuredSelection)) {
