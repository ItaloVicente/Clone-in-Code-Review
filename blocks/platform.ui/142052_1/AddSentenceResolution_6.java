		if (editorPart == null) {
			try {
				editorPart = IDE.openEditor(page, (IFile) marker.getResource(), true);
			} catch (PartInitException e) {
				MessageDialog.openError(w.getShell(), MessageUtil.getString("Resolution_Error"), //$NON-NLS-1$
						MessageUtil.getString("Unable_to_open_file_editor")); //$NON-NLS-1$
			}
		}
		if (editorPart == null || !(editorPart instanceof ReadmeEditor))
			return;
		ReadmeEditor editor = (ReadmeEditor) editorPart;
		IDocument doc = editor.getDocumentProvider().getDocument(editor.getEditorInput());
		String s = MessageUtil.getString("Simple_sentence"); //$NON-NLS-1$
		try {
			doc.replace(marker.getAttribute(IMarker.CHAR_START, -1), 0, s);
		} catch (BadLocationException e) {
			return;
		}
		try {
			marker.delete();
		} catch (CoreException e) {
			e.printStackTrace();
		}
