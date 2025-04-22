		final Command cmd = cmdService
				.getCommand(IWorkbenchCommandConstants.WINDOW_CLOSE_PART);
		IHandlerService handlerService = getWorkbench()
				.getService(IHandlerService.class);
		final ExecutionEvent event = handlerService.createExecutionEvent(cmd,
				null);
