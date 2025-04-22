			StructuredSelection sel = null;
			if (part instanceof IEditorPart) {
				IResource resource = getResource((IEditorPart) part);
				if (resource != null) {
					sel = new StructuredSelection(resource);
				}
			} else {
				ISelection selection = partRef.getPage().getSelection();
				if (selection instanceof StructuredSelection) {
					sel = (StructuredSelection) selection;
				}
			}
