		for (int i= 0; i < windows.length; i++) {
			IWorkbenchPage[] pages= windows[i].getPages();
			for (int j= 0; j < pages.length; j++) {
				IEditorReference[] editorReferences= pages[j].getEditorReferences();
				for (int k= 0; k < editorReferences.length; k++)
					closeEditor(editorReferences[k].getEditor(false));
