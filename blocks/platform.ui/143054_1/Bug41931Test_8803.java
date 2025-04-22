		IEditorPart[] expectedResults = { editorA, editorB, editorC };
		IWorkbenchPartReference[] actualResults = page.getSortedParts();
		for (int i = 0; i < expectedResults.length; i++) {
			assertEquals(
					"Pre-test order is not correct.", expectedResults[i].getTitle(), actualResults[i].getPart(false).getTitle()); //$NON-NLS-1$
		}
