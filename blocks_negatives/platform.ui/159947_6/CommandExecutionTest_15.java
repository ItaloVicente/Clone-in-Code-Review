		final Command cmd = cmdService
				.getCommand(IWorkbenchCommandConstants.FILE_CLOSE_OTHERS);
		IHandlerService handlerService = getWorkbench()
				.getService(IHandlerService.class);
		final ExecutionEvent event = handlerService.createExecutionEvent(cmd,
				null);
