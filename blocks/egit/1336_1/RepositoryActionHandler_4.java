	protected IStructuredSelection getSelection() {
		IWorkbenchWindow activeWorkbenchWindow = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow();
		if (activeWorkbenchWindow == null) // During Eclipse shutdown there is
			return StructuredSelection.EMPTY;
		IHandlerService hsr = (IHandlerService) activeWorkbenchWindow
				.getService(IHandlerService.class);
		IEvaluationContext ctx = hsr.getCurrentState();
		Object selection = ctx.getVariable(ISources.ACTIVE_MENU_SELECTION_NAME);
