	private void notifyPostSelectionListeners(String id, IWorkbenchPart workbenchPart, ISelection selection) {
		for (Object listener : postSelectionListeners.getListeners()) {
			if (selection != null || listener instanceof INullSelectionListener) {
				((ISelectionListener) listener).selectionChanged(workbenchPart, selection);
			}
		}
		
