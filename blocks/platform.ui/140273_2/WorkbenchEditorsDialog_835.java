			}
			return image;
		}

		private void activate() {
			if (editorRef != null) {
				IEditorPart editor = editorRef.getEditor(true);
				WorkbenchPage p = (WorkbenchPage) editor.getEditorSite().getPage();
				Shell s = p.getWorkbenchWindow().getShell();
				if (s.getMinimized()) {
