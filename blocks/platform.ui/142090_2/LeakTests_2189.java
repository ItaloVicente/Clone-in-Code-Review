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
