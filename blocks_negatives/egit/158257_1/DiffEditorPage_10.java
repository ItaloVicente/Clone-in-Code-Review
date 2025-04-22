		super.doSetSelection(selection);
	}

	@Override
	protected void editorContextMenuAboutToShow(IMenuManager menu) {
		super.editorContextMenuAboutToShow(menu);
		addAction(menu, ITextEditorActionConstants.GROUP_COPY,
				ITextEditorActionConstants.SELECT_ALL);
		menu.remove(ITextEditorActionConstants.SHIFT_RIGHT);
		menu.remove(ITextEditorActionConstants.SHIFT_LEFT);
