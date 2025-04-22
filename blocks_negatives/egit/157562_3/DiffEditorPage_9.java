	protected void editorContextMenuAboutToShow(IMenuManager menu) {
		super.editorContextMenuAboutToShow(menu);
		addAction(menu, ITextEditorActionConstants.GROUP_COPY,
				ITextEditorActionConstants.SELECT_ALL);
		menu.remove(ITextEditorActionConstants.SHIFT_RIGHT);
		menu.remove(ITextEditorActionConstants.SHIFT_LEFT);
	}

	@Override
	protected void rulerContextMenuAboutToShow(IMenuManager menu) {
		super.rulerContextMenuAboutToShow(menu);
		IContributionItem showLineNumbers = menu
				.find(ITextEditorActionConstants.LINENUMBERS_TOGGLE);
		boolean isShowingLineNumbers = EditorsUI.getPreferenceStore()
				.getBoolean(
						AbstractDecoratedTextEditorPreferenceConstants.EDITOR_LINE_NUMBER_RULER);
		if (showLineNumbers instanceof ActionContributionItem) {
			((ActionContributionItem) showLineNumbers).getAction()
					.setChecked(isShowingLineNumbers);
		}
		if (isShowingLineNumbers) {
			boolean plain = lineNumberColumn.isPlain();
			IAction togglePlain = new Action(
					UIText.DiffEditorPage_ToggleLineNumbers,
					IAction.AS_CHECK_BOX) {

				@Override
				public void run() {
					plainLineNumbers = !plain;
					lineNumberColumn.setPlain(!plain);
				}
			};
			togglePlain.setChecked(!plain);
			menu.appendToGroup(ITextEditorActionConstants.GROUP_RULERS,
					togglePlain);
		}
