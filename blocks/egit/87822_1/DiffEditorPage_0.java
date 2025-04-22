		if (viewer != null) {
			int offset = viewer.widgetOffset2ModelOffset(widgetOffset);
			IDocument document = getDocumentProvider()
					.getDocument(getEditorInput());
			if (document instanceof DiffDocument) {
				return ((DiffDocument) document).findFileRegion(offset);
			}
