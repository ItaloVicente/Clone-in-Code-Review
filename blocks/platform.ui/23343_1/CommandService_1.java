		String helpContextId = null;
		IHandler handler = command.getHandler();
		if (handler instanceof HandlerServiceHandler) {
			handler = ((HandlerServiceHandler) handler).getDirectHandler();
		}
		helpContextId = helpContextIdsByHandler.get(handler);
		if (helpContextId == null) {
			helpContextId = commandManager.getHelpContextId(command);
		}
		return helpContextId;
