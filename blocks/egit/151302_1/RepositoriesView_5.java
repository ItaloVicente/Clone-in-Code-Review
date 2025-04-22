	private void showEditor(FileNode node) {
		File file = node.getObject();
		IWorkbenchWindow window = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow();
		if (window != null) {
			IWorkbenchPage page = window.getActivePage();
			IEditorPart editor = EgitUiEditorUtils.findEditor(file, page);
			IEditorPart active = page.getActiveEditor();
			if (editor != null && editor != active) {
				window.getWorkbench().getDisplay()
						.asyncExec(() -> page.bringToTop(editor));
			}
		}
	}

