	private void updateState() {
		IWorkbenchPage page = getActivePage();
		if (page == null) {
			setEnabled(false);
			return;
		}
		IEditorReference editors[] = page.getEditorReferences();
		for (int i = 0; i < editors.length; i++) {
			if (!editors[i].isDirty()) {
				setEnabled(true);
				return;
			}
		}
		setEnabled(false);
	}
