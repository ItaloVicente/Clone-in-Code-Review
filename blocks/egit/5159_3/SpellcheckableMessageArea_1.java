		final boolean editable = isEditable(sourceViewer);
		final TextViewerAction cutAction;
		final TextViewerAction undoAction;
		final TextViewerAction redoAction;
		final TextViewerAction pasteAction;
		if (editable) {
			cutAction = new TextViewerAction(sourceViewer,
					ITextOperationTarget.CUT);
			cutAction.setText(UIText.SpellCheckingMessageArea_cut);
			cutAction
					.setActionDefinitionId(IWorkbenchCommandConstants.EDIT_CUT);

			undoAction = new TextViewerAction(sourceViewer,
					ITextOperationTarget.UNDO);
			undoAction.setText(UIText.SpellcheckableMessageArea_undo);
			undoAction
					.setActionDefinitionId(IWorkbenchCommandConstants.EDIT_UNDO);

			redoAction = new TextViewerAction(sourceViewer,
					ITextOperationTarget.REDO);
			redoAction.setText(UIText.SpellcheckableMessageArea_redo);
			redoAction
					.setActionDefinitionId(IWorkbenchCommandConstants.EDIT_REDO);

			pasteAction = new TextViewerAction(sourceViewer,
					ITextOperationTarget.PASTE);
			pasteAction.setText(UIText.SpellCheckingMessageArea_paste);
			pasteAction
					.setActionDefinitionId(IWorkbenchCommandConstants.EDIT_PASTE);
		} else {
			cutAction = null;
			undoAction = null;
			redoAction = null;
			pasteAction = null;
		}
