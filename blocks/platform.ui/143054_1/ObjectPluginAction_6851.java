		IWorkbenchPart part = partRef.getPart(false);
		if (part instanceof MultiPageEditorPart && activePart instanceof IEditorPart) {
			MultiPageEditorPart mulitPageEditorPart = (MultiPageEditorPart) part;
			IEditorPart[] editorsForActivePart = mulitPageEditorPart
					.findEditors(((IEditorPart) activePart).getEditorInput());
			if (editorsForActivePart != null && editorsForActivePart.length > 0) {
				clearActivePart();
				return;
			}
		}
		if (activePart != null && part == activePart) {
			clearActivePart();
