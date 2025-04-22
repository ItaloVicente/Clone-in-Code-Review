
		if (part instanceof IEditorPart) {
			IEditorInput input = ((IEditorPart) part).getEditorInput();
			if (input instanceof IFileEditorInput) {
				reactOnSelection(new StructuredSelection(
						((IFileEditorInput) input).getFile()));
			} else if (input instanceof IURIEditorInput) {
				reactOnSelection(new StructuredSelection(input));
			}

