        expectedResults = new IEditorPart[] { editorA, editorC, editorB };
        actualResults = page.getSortedParts();
        for (int i = 0; i < expectedResults.length; i++) {
            assertEquals(
                    "bringToTop() does not change sorted part order.", expectedResults[i].getTitle(), actualResults[i].getPart(false).getTitle()); //$NON-NLS-1$
        }
    }
