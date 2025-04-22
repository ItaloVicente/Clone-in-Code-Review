    /**
     * Tests the equals and hashCode methods.
     */
    public void testEqualsAndHash() {
        String ea = "dummy.editor.id.A";
        String eb = "dummy.editor.id.B";
        String ec = "dummy.editor.id.C";
        IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
        IEditorInput ia = new FileEditorInput(root.getFile(new Path("/DummyProject/FileA")));
        IEditorInput ib = new FileEditorInput(root.getFile(new Path("/DummyProject/FileB")));
        IEditorInput ic = new FileEditorInput(root.getFile(new Path("/DummyProject/FileC")));
        MultiEditorInput a = new MultiEditorInput(new String[] { ea }, new IEditorInput[] { ia });
        MultiEditorInput a2 = new MultiEditorInput(new String[] { ea }, new IEditorInput[] { ia });
        MultiEditorInput b = new MultiEditorInput(new String[] { eb }, new IEditorInput[] { ib });
        MultiEditorInput abc = new MultiEditorInput(new String[] { ea, eb, ec }, new IEditorInput[] { ia, ib, ic });
        MultiEditorInput abc2 = new MultiEditorInput(new String[] { ea, eb, ec }, new IEditorInput[] { ia, ib, ic });
