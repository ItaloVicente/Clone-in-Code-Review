
		showInitialSelection();
	}

	/**
	 * try to automatically show a repository when opening the view
	 */
	private void showInitialSelection() {
		IWorkbenchWindow window = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow();
		if (window == null) {
			return;
		}
		IEditorPart editor = window.getActivePage().getActiveEditor();
		selectionChangedListener.selectionChanged(editor,
				window.getSelectionService().getSelection());
