		IWorkbenchPart part = partRef.getPart(false);
		if (part instanceof MultiPageEditorPart) {
			MultiPageEditorPart mulitPageEditorPart = (MultiPageEditorPart) part;
			if (activePart instanceof IEditorPart) {
				IEditorPart[] editorsForActivePart = mulitPageEditorPart
						.findEditors(((IEditorPart) activePart).getEditorInput());
				if (editorsForActivePart != null && editorsForActivePart.length > 0) {
					clearActivePart();
				}
			}
