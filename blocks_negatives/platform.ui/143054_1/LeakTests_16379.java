    public void testTextEditorContextMenu() throws Exception {
    	proj = FileUtil.createProject("testEditorLeaks");

    	IEditorInput input = new NullEditorInput();
        ReferenceQueue queue = new ReferenceQueue();
        IEditorPart editor = IDE.openEditor(fActivePage, input, "org.eclipse.ui.tests.leak.contextEditor");
        assertTrue(editor instanceof ContextEditorPart);
        Reference ref = createReference(queue, editor);

        ContextEditorPart contextMenuEditor = (ContextEditorPart) editor;

        contextMenuEditor.showMenu();
        processEvents();

        contextMenuEditor.hideMenu();
        processEvents();

        try {
            contextMenuEditor = null;
            fActivePage.closeEditor(editor, false);
            editor = null;
            checkRef(queue, ref);
        } finally {
            ref.clear();
        }
    }

      /**
       * No idea why the following test is failing.  Doug has ran this through a
       * profiler and for some reason the window just isn't being GCd despite
       * there not being nay incoming references.
       */

    /**
     * Test for leaks if dialog is disposed before it is closed.
     * This is really testing the framework rather than individual
     * dialogs, since many dialogs or windows will fail if the shell
     * is destroyed prior to closing them.
     */
  public void testDestroyedDialogLeaks() throws Exception {
	  ReferenceQueue queue = new ReferenceQueue();
	  Dialog newDialog = new SaveAsDialog(fWin.getShell());
      newDialog.setBlockOnOpen(false);
      newDialog.open();
      assertNotNull(newDialog);
      Reference ref = createReference(queue, newDialog);
      try {
       	  newDialog.getShell().dispose();
       	  newDialog.close();
       	  newDialog = null;
          checkRef(queue, ref);
      } finally {
    	  ref.clear();
      }
  }
