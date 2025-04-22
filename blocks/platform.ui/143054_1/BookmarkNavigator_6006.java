	}

	Shell getShell() {
		return getViewSite().getShell();
	}

	StructuredViewer getViewer() {
		return viewer;
	}

	IWorkspace getWorkspace() {
		return ResourcesPlugin.getWorkspace();
	}

	void handleKeyPressed(KeyEvent event) {
		if (event.character == SWT.DEL && event.stateMask == 0
				&& removeAction.isEnabled()) {
