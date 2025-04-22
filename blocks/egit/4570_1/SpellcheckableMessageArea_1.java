				IHandlerService service = (IHandlerService) PlatformUI
						.getWorkbench().getService(IHandlerService.class);
				cutHandlerActivation = service.activateHandler(
						IWorkbenchCommandConstants.EDIT_CUT, new ActionHandler(
								cutAction), new ActiveShellExpression(
								getParent().getShell()));
				copyHandlerActivation = service.activateHandler(
						IWorkbenchCommandConstants.EDIT_COPY,
						new ActionHandler(copyAction),
						new ActiveShellExpression(getParent().getShell()));
				pasteHandlerActivation = service.activateHandler(
						IWorkbenchCommandConstants.EDIT_PASTE,
						new ActionHandler(pasteAction),
						new ActiveShellExpression(getParent().getShell()));
				selectAllHandlerActivation = service.activateHandler(
						IWorkbenchCommandConstants.EDIT_SELECT_ALL,
						new ActionHandler(selectAllAction),
						new ActiveShellExpression(getParent().getShell()));
				undoHandlerActivation = service.activateHandler(
						IWorkbenchCommandConstants.EDIT_UNDO,
						new ActionHandler(undoAction),
						new ActiveShellExpression(getParent().getShell()));
				redoHandlerActivation = service.activateHandler(
						IWorkbenchCommandConstants.EDIT_REDO,
						new ActionHandler(redoAction),
						new ActiveShellExpression(getParent().getShell()));
