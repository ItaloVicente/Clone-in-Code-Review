		for (int i = 0; i < containingParts.length; i++) {
			IWorkbenchPart workbenchPart = containingParts[i];
			if (workbenchPart instanceof IEditorPart) {
				IEditorPart editorPart = (IEditorPart) workbenchPart;
				if (isEditingDescendantOf(editorPart)) {
					return true;
				}
			}
