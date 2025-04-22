	@SuppressWarnings("unchecked")
	@Override
	public Object getAdapter(Class adapter) {
		if (IContentOutlinePage.class.equals(adapter)) {
			if (outlinePage == null) {
				outlinePage = createOutlinePage();
				outlinePage.setInput(
						getDocumentProvider().getDocument(getEditorInput()));
				if (currentFileDiffRange != null) {
					outlinePage.setSelection(
							new StructuredSelection(currentFileDiffRange));
				}
			}
			return outlinePage;
		}
		return super.getAdapter(adapter);
	}

	private DiffEditorOutlinePage createOutlinePage() {
		DiffEditorOutlinePage page = new DiffEditorOutlinePage();
		page.addSelectionChangedListener(
				event -> doSetSelection(event.getSelection()));
		page.addOpenListener(event -> {
			FormEditor editor = getEditor();
			editor.getSite().getPage().activate(editor);
			editor.setActivePage(getId());
			doSetSelection(event.getSelection());
		});
		return page;
	}

	@Override
	public void dispose() {
		if (outlinePage != null) {
			outlinePage.dispose();
			outlinePage = null;
		}
		super.dispose();
	}

