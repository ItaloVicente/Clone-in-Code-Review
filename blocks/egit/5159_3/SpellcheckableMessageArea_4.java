				if (undoAction != null)
					undoHandlerActivation = service.activateHandler(
							IWorkbenchCommandConstants.EDIT_UNDO,
							new ActionHandler(undoAction),
							new ActiveShellExpression(getParent().getShell()));
				if (redoAction != null)
					redoHandlerActivation = service.activateHandler(
							IWorkbenchCommandConstants.EDIT_REDO,
							new ActionHandler(redoAction),
							new ActiveShellExpression(getParent().getShell()));
