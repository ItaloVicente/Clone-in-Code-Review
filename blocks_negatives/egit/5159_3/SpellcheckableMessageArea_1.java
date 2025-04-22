				IHandlerService service = (IHandlerService) PlatformUI.getWorkbench().getService(IHandlerService.class);
				this.cutHandlerActivation = service.activateHandler(IWorkbenchCommandConstants.EDIT_CUT, new ActionHandler(cutAction), new ActiveShellExpression(getParent().getShell()));
	            this.copyHandlerActivation = service.activateHandler(IWorkbenchCommandConstants.EDIT_COPY, new ActionHandler(copyAction), new ActiveShellExpression(getParent().getShell()));
	            this.pasteHandlerActivation = service.activateHandler(IWorkbenchCommandConstants.EDIT_PASTE, new ActionHandler(pasteAction), new ActiveShellExpression(getParent().getShell()));
	            this.selectAllHandlerActivation = service.activateHandler(IWorkbenchCommandConstants.EDIT_SELECT_ALL, new ActionHandler(selectAllAction), new ActiveShellExpression(getParent().getShell()));
				undoHandlerActivation = service.activateHandler(
						IWorkbenchCommandConstants.EDIT_UNDO,
						new ActionHandler(undoAction),
