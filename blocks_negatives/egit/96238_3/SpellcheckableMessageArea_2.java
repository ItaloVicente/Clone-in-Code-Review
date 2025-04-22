
		final TextViewerAction copyAction = new TextViewerAction(sourceViewer,
				ITextOperationTarget.COPY);
		copyAction.setText(UIText.SpellCheckingMessageArea_copy);
		copyAction.setActionDefinitionId(IWorkbenchCommandConstants.EDIT_COPY);

		final TextViewerAction selectAllAction = new TextViewerAction(
				sourceViewer, ITextOperationTarget.SELECT_ALL);
		selectAllAction.setText(UIText.SpellCheckingMessageArea_selectAll);
		selectAllAction
				.setActionDefinitionId(IWorkbenchCommandConstants.EDIT_SELECT_ALL);
