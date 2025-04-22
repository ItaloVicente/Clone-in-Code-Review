	public IHandler getDirectHandler() {
		ExecutionContexts contexts = HandlerServiceImpl.peek();
		if (contexts != null) {
			Object handler = HandlerServiceImpl.lookUpHandler(contexts.context, commandId);
			if (handler instanceof IHandler) {
				return (IHandler) handler;
			}
		}
		return null;
	}

