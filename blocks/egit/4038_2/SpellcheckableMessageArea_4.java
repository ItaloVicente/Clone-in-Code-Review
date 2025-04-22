	private ActionHandler createContentAssistActionHandler(
			final ITextOperationTarget textOperationTarget) {
		Action proposalAction = new Action() {
			public void run() {
				if (textOperationTarget
						.canDoOperation(ISourceViewer.CONTENTASSIST_PROPOSALS))
					textOperationTarget
							.doOperation(ISourceViewer.CONTENTASSIST_PROPOSALS);
			}
		};
		proposalAction
				.setActionDefinitionId(ITextEditorActionDefinitionIds.CONTENT_ASSIST_PROPOSALS);
		return new ActionHandler(proposalAction);
	}

