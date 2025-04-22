			cutAction = createFromActionFactory(ActionFactory.CUT,
					ITextOperationTarget.CUT);
			undoAction = createFromActionFactory(ActionFactory.UNDO,
					ITextOperationTarget.UNDO);
			redoAction = createFromActionFactory(ActionFactory.REDO,
					ITextOperationTarget.REDO);
			pasteAction = createFromActionFactory(ActionFactory.PASTE,
					ITextOperationTarget.PASTE);
