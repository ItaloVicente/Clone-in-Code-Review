        IWorkbenchPage page = openTestWindow().getActivePage();
        IEditorPart editor1 = page.openEditor(new FileEditorInput(file1), MATCHING_EDITOR_ID);
        IEditorPart editor1b = page.openEditor(new FileEditorInput(file1), MATCHING_EDITOR_ID);
        IEditorPart editor2 = page.openEditor(new FileEditorInput(file2), MATCHING_EDITOR_ID);
        IEditorPart editor3 = page.openEditor(new FileEditorInput(file3), MATCHING_EDITOR_ID);
        IEditorPart editor4 = page.openEditor(new FileEditorInput(file4), MATCHING_EDITOR_ID);
        assertSame(editor1, editor1b);
        assertSame(editor1, editor2);
        assertSame(editor1, editor3);
        assertNotSame(editor1, editor4);
    }
