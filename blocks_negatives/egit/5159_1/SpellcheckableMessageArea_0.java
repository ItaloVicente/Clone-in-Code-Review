		selectAllAction.setActionDefinitionId(IWorkbenchCommandConstants.EDIT_SELECT_ALL);

		final TextViewerAction undoAction = new TextViewerAction(sourceViewer,
				ITextOperationTarget.UNDO);
		undoAction.setText(UIText.SpellcheckableMessageArea_undo);
		undoAction.setActionDefinitionId(IWorkbenchCommandConstants.EDIT_UNDO);

		final TextViewerAction redoAction = new TextViewerAction(sourceViewer,
				ITextOperationTarget.REDO);
		redoAction.setText(UIText.SpellcheckableMessageArea_redo);
		redoAction.setActionDefinitionId(IWorkbenchCommandConstants.EDIT_REDO);
