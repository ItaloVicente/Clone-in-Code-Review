			textOperationTarget.doOperation(ISourceViewer.QUICK_ASSIST);
		}

	}

	private ActionHandler createQuickFixActionHandler(
			final ITextOperationTarget textOperationTarget) {
		Action quickFixAction = new QuickfixAction(textOperationTarget);
		quickFixAction.setActionDefinitionId(
				ITextEditorActionDefinitionIds.QUICK_ASSIST);
		return new ActionHandler(quickFixAction);
	}

	private static class ContentAssistAction extends Action {

		private final SourceViewer viewer;

		public ContentAssistAction(SourceViewer viewer) {
			this.viewer = viewer;
		}

		@Override
		public void run() {
			if (viewer.canDoOperation(ISourceViewer.CONTENTASSIST_PROPOSALS)
					&& viewer.getTextWidget().isFocusControl()) {
				viewer.doOperation(ISourceViewer.CONTENTASSIST_PROPOSALS);
