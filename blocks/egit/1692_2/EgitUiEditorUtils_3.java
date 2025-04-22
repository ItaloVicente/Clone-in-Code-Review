	public static void openErrorEditor(IWorkbenchPage page,
			IStatus errorStatus, String title, String tooltip) {
		IEditorReference[] refs = page.findEditors(null,
				EgitErrorEditor.EDITOR_ID, IWorkbenchPage.MATCH_ID);
		IEditorInput erorreditorinput = EgitErrorEditor.createInput(
				errorStatus, title, tooltip);
		try {
			if (refs.length == 0)
				IDE.openEditor(page, erorreditorinput,
						EgitErrorEditor.EDITOR_ID, false);
			else {
				IEditorPart editor = refs[0].getEditor(true);
				editor.init(editor.getEditorSite(), erorreditorinput);
				editor.getEditorSite().getPage().bringToTop(editor);
			}
		} catch (PartInitException e) {
			Activator.handleError(e.getMessage(), e, false);
		}
	}

