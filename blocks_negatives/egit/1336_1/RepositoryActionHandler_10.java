		if (event != null)
			selection = HandlerUtil.getCurrentSelectionChecked(event);
		else {
			IWorkbenchWindow activeWorkbenchWindow = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
				return new StructuredSelection();
			IHandlerService hsr = (IHandlerService) activeWorkbenchWindow.getService(IHandlerService.class);
			IEvaluationContext ctx = hsr.getCurrentState();
			selection = (ISelection) ctx.getVariable(ISources.ACTIVE_MENU_SELECTION_NAME);
			if (selection == null)
				throw new ExecutionException(
						UIText.RepositoryActionHandler_CouldNotGetSelection_message);
