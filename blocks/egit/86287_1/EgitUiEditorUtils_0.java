
	public static void revealLine(IEditorPart editor, int lineNo) {
		if (lineNo < 0) {
			return;
		}
		ITextEditor textEditor = getTextEditor(editor);
		if (textEditor == null) {
			return;
		}
		IDocument document = textEditor.getDocumentProvider()
				.getDocument(textEditor.getEditorInput());
		if (document == null) {
			return;
		}
		try {
			textEditor.selectAndReveal(document.getLineOffset(lineNo), 0);
		} catch (BadLocationException e) {
		}
	}

	private static ITextEditor getTextEditor(IEditorPart editor) {
		if (editor instanceof ITextEditor) {
			return (ITextEditor) editor;
		} else if (editor instanceof MultiPageEditorPart) {
			Object nestedEditor = ((MultiPageEditorPart) editor)
					.getSelectedPage();
			if (nestedEditor instanceof ITextEditor) {
				return (ITextEditor) nestedEditor;
			}
		}
		return null;
	}
