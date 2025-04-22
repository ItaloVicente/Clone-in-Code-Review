        super.doTearDown();
        fWin = null;
        fActivePage = null;
        if (proj != null) {
            FileUtil.deleteProject(proj);
            proj = null;
        }
    }

    public void testSimpleEditorLeak() throws Exception {
        proj = FileUtil.createProject("testEditorLeaks");

        IFile file = FileUtil.createFile("test.mock1", proj);

        ReferenceQueue queue = new ReferenceQueue();
        IEditorPart editor = IDE.openEditor(fActivePage, file);
        assertNotNull(editor);
        Reference ref = createReference(queue, editor);
        try {
            fActivePage.closeEditor(editor, false);
            editor = null;
            checkRef(queue, ref);
        } finally {
            ref.clear();
        }
    }

    public void testSimpleViewLeak() throws Exception {
        ReferenceQueue queue = new ReferenceQueue();
        IViewPart view = fActivePage.showView(MockViewPart.ID);
        assertNotNull(view);
        Reference ref = createReference(queue, view);

        try {
            fActivePage.hideView(view);
            view = null;
            checkRef(queue, ref);
        } finally {
            ref.clear();
        }
    }
