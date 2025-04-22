		IHandlerService service = PlatformUI.getWorkbench()
				.getService(IHandlerService.class);
		showViewHandler = service
				.activateHandler(
						IWorkbenchCommandConstants.WINDOW_SHOW_VIEW_MENU,
						new ActionHandler(popupMenuAction),
						new ActiveShellExpression(getShell()));
