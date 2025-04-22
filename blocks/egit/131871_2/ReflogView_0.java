
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
			Repository repository = AdapterUtils.adapt(input, Repository.class);
			if (repository != null) {
				reactOnSelection(new StructuredSelection(repository));
			}
		} else {
			IStructuredSelection selection = (IStructuredSelection) window
					.getSelectionService().getSelection();
			reactOnSelection(selection);
		}
