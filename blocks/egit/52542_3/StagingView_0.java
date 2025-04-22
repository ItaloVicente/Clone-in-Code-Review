	private void reactOnInitialSelection() {
		StructuredSelection sel = null;
		if (initialSelection instanceof StructuredSelection) {
			sel = (StructuredSelection) initialSelection;
		} else if (initialSelection != null && !initialSelection.isEmpty()) {
			sel = getSelectionOfActiveEditor();
		}
		if (sel != null) {
			reactOnSelection(sel);
		}
		initialSelection = null;
	}

	private StructuredSelection getSelectionOfActiveEditor() {
		IEditorPart activeEditor = getSite().getPage().getActiveEditor();
		if (activeEditor == null) {
			return null;
		}
		return getSelectionOfPart(activeEditor);
	}

	private static StructuredSelection getSelectionOfPart(IWorkbenchPart part) {
		StructuredSelection sel = null;
		if (part instanceof IEditorPart) {
			IResource resource = getResource((IEditorPart) part);
			if (resource != null) {
				sel = new StructuredSelection(resource);
			}
		} else {
			ISelection selection = part.getSite().getPage().getSelection();
			if (selection instanceof StructuredSelection) {
				sel = (StructuredSelection) selection;
			}
		}
		return sel;
	}

	private static IResource getResource(IEditorPart part) {
		IEditorInput input = part.getEditorInput();
		if (input instanceof IFileEditorInput) {
			return ((IFileEditorInput) input).getFile();
		} else {
			return CommonUtils.getAdapter(input, IResource.class);
		}
	}

