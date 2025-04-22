		getSite().getPage().removePartListener(partListener);
		super.dispose();
	}

	void editorActivated(IEditorPart editor) {
		if (!isLinkingEnabled()) {
