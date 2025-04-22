		ISelection selection;

		IHandlerService hsr = (IHandlerService) PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getService(IHandlerService.class);
		IEvaluationContext ctx = hsr.getCurrentState();
		selection = (ISelection) ctx
				.getVariable(ISources.ACTIVE_MENU_SELECTION_NAME);

		if (selection instanceof IStructuredSelection)
			return (IStructuredSelection) selection;
		return new StructuredSelection();
