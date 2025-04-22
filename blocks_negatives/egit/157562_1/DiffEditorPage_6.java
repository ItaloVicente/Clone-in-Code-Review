		this(editor, "diffPage", UIText.DiffEditorPage_Title); //$NON-NLS-1$
	}

	@Override
	public <T> T getAdapter(Class<T> adapter) {
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
			return adapter.cast(outlinePage);
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
