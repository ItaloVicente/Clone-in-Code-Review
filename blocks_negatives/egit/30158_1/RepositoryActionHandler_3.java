	private static IEvaluationContext getEvaluationContext() {
		IEvaluationContext ctx;
		IWorkbenchWindow activeWorkbenchWindow = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow();
		if (activeWorkbenchWindow == null)
			return null;
		IHandlerService hsr = (IHandlerService) activeWorkbenchWindow
				.getService(IHandlerService.class);
		ctx = hsr.getCurrentState();
		return ctx;
	}

