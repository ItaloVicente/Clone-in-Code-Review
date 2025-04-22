	private IWorkbenchPart determinePart(IWorkbenchPart part) {
		IWorkbenchPart currentPart = part;
		if (currentPart instanceof IEditorPart) {
			if (currentPart instanceof MultiPageEditorPart) {
				Object nestedEditor = ((MultiPageEditorPart) part)
						.getSelectedPage();
				if (nestedEditor instanceof IEditorPart) {
					currentPart = ((IEditorPart) nestedEditor);
				}
			}
		}
		return currentPart;
	}

	private ISelection convertSelection(IWorkbenchPart part,
			ISelection selection) {
		if (part instanceof IEditorPart) {
			IEditorInput input = ((IEditorPart) part).getEditorInput();
			if (input instanceof IFileEditorInput) {
				return new StructuredSelection(
						((IFileEditorInput) input).getFile());
			} else if (input instanceof IURIEditorInput) {
				return new StructuredSelection(input);
			}
		}
		return selection;
	}

