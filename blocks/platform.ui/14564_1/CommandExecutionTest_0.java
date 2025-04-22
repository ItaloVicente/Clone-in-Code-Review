		verifyHandlerUtilAccessDuringPreExecute(listener);
	}

	private void verifyHandlerUtilAccessDuringPreExecute(EL listener) {
		assertNotNull(
				"HandlerUtil.getActiveWorkbenchWindow() returned null during ICommandListener.preExecute().",
				listener.wbw);
