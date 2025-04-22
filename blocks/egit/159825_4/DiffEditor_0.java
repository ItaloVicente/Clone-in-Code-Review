		menu.appendToGroup(ITextEditorActionConstants.GROUP_OPEN,
				getAction(QUICK_OUTLINE_COMMAND));
	}

	@Override
	protected void createActions() {
		super.createActions();
		createQuickOutlineAction();
	}

	private void createQuickOutlineAction() {
		Action outlineAction = new Action(
				UIText.DiffEditor_QuickOutlineAction) {
			@Override
			public void run() {
				DiffEditorOutlinePage.openQuickOutline(
						getDocumentProvider().getDocument(getEditorInput()),
						getEditorSite().getSelectionProvider());
			}
		};
		outlineAction.setActionDefinitionId(QUICK_OUTLINE_COMMAND);
		setAction(outlineAction.getActionDefinitionId(), outlineAction);
