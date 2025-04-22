				undoHandlerActivation = service.activateHandler(
						IWorkbenchCommandConstants.EDIT_UNDO,
						new ActionHandler(undoAction),
						new ActiveShellExpression(getParent().getShell()));
				redoHandlerActivation = service.activateHandler(
						IWorkbenchCommandConstants.EDIT_REDO,
						new ActionHandler(redoAction),
						new ActiveShellExpression(getParent().getShell()));
