	    for (IWorkbenchPart workbenchPart : containingParts) {
		if (workbenchPart instanceof IEditorPart) {
		    IEditorPart editorPart = (IEditorPart) workbenchPart;
		    if (isEditingDescendantOf(editorPart)) {
			return true;
		    }
