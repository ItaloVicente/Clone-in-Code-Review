		if (lineNumberToReveal >= 0) {
			IDocument document = editor.getDocumentProvider().getDocument(
					editor.getEditorInput());
			int offset;
			try {
				offset = document.getLineOffset(lineNumberToReveal);
				editor.selectAndReveal(offset, 0);
			} catch (BadLocationException e) {
			}
		}

