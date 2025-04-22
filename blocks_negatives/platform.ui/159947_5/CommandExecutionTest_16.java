		final Command cmd = cmdService
				.getCommand(IWorkbenchCommandConstants.FILE_REFRESH);
		IHandlerService handlerService = getWorkbench()
				.getService(IHandlerService.class);
		final ExecutionEvent event = handlerService.createExecutionEvent(cmd,
				null);
