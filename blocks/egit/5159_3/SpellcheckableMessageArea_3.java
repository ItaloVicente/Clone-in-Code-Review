				if (pasteAction != null)
					this.pasteHandlerActivation = service.activateHandler(
							IWorkbenchCommandConstants.EDIT_PASTE,
							new ActionHandler(pasteAction),
							new ActiveShellExpression(getParent().getShell()));
				selectAllHandlerActivation = service.activateHandler(
						IWorkbenchCommandConstants.EDIT_SELECT_ALL,
						new ActionHandler(selectAllAction),
