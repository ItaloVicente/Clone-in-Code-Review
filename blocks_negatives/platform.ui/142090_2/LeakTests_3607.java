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
