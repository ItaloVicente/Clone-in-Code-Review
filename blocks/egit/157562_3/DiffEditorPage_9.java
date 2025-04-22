	protected void setSelectionAndActivate(ISelection selection,
			boolean activate) {
		if (activate) {
			FormEditor editor = getEditor();
			editor.getSite().getPage().activate(editor);
			editor.setActivePage(getId());
