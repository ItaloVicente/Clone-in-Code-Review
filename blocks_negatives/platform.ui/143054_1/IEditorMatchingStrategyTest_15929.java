        IWorkbenchPage page = openTestWindow().getActivePage();
        IEditorPart editor = page.openEditor(new FileEditorInput(file1), MATCHING_EDITOR_ID);
        assertEquals(editor, page.findEditor(new FileEditorInput(file1)));
        assertEquals(editor, page.findEditor(new FileEditorInput(file2)));
        assertEquals(editor, page.findEditor(new FileEditorInput(file3)));
        assertEquals(null, page.findEditor(new FileEditorInput(file4)));
    }
