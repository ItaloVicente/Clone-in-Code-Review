
	public MHandler findHandler(MHandlerContainer handlerContainer, String id) {
		if (handlerContainer == null) {
			return null;
		}

		for (MHandler handler : handlerContainer.getHandlers()) {
			if (match(handler, id, null, null)) {
				return handler;
			}
		}
		return null;
	}
