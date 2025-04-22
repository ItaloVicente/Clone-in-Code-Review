		return true;
	}

	private void open(EditorHistoryItem item) {
		IWorkbenchPage page = window.getActivePage();
		if (page != null) {
			try {
				String itemName = item.getName();
				if (!item.isRestored()) {
					item.restoreState();
				}
				IEditorInput input = item.getInput();
				IEditorDescriptor desc = item.getDescriptor();
