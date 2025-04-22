
		showInitialSelection();
	}

	private void showInitialSelection() {
		IWorkbenchWindow window = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow();
		if (window == null) {
			return;
		}
		IEditorPart editor = window.getActivePage().getActiveEditor();
		if (editor != null) {
			IEditorInput input = editor.getEditorInput();
			if (input instanceof IFileEditorInput)
				reactOnSelection(new StructuredSelection(
						((IFileEditorInput) input).getFile()));
		} else {
			IStructuredSelection selection = (IStructuredSelection) window
					.getSelectionService().getSelection();
			reactOnSelection(selection);
		}
