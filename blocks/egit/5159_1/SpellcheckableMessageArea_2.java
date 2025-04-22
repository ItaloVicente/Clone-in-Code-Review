				IHandlerService service = (IHandlerService) PlatformUI
						.getWorkbench().getService(IHandlerService.class);
				if (cutAction != null) {
					cutAction.update();
					cutHandlerActivation = service.activateHandler(
							IWorkbenchCommandConstants.EDIT_CUT,
							new ActionHandler(cutAction),
							new ActiveShellExpression(getParent().getShell()));
				}
