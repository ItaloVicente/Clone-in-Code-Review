		IEditorInput input = editor.getEditorInput();
		if (input instanceof IFileEditorInput) {
			IFileEditorInput fileInput = (IFileEditorInput) input;
			IFile file = fileInput.getFile();
			ISelection newSelection = new StructuredSelection(file);
			if (!viewer.getSelection().equals(newSelection)) {
				viewer.setSelection(newSelection);
			}
		}

	}

	void fillContextMenu(IMenuManager menu) {
		actionGroup.setContext(new ActionContext(getViewer().getSelection()));
		actionGroup.fillContextMenu(menu);
	}

	IContainer getInitialInput() {
		IAdaptable input = getSite().getPage().getInput();
