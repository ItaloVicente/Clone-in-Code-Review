	private IContentOutlinePage getOutlinePage() {
		if (outlinePage == null) {
			outlinePage = new MultiPageEditorContentOutlinePage(this);
		}
		return outlinePage;
	}

