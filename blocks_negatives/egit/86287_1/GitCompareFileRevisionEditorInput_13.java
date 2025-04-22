			selectLine(editor, selectedLine);
		}

		private void selectLine(IEditorPart editorPart, int selectedLine) {
			if (editorPart instanceof ITextEditor) {
				ITextEditor editor = (ITextEditor) editorPart;
				IDocument document = editor.getDocumentProvider().getDocument(
						editor.getEditorInput());
				if (document != null)
					try {
						IRegion line = document
								.getLineInformation(selectedLine);
						editor.selectAndReveal(line.getOffset(), 0);
					} catch (BadLocationException e) {
					}
			}
