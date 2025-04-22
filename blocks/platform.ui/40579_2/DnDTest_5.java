		TextEditor editorPart = null;
		try {
			editorPart = (TextEditor) IDE.openEditor(activePage, file);
		} catch (PartInitException e) {
			fail("Should not throw an exception");
		}
