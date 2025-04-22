	@Override
	public MHandler findHandler(MHandlerContainer handlerContainer, String id) {
		if (handlerContainer == null || id == null || id.length() == 0) {
			return null;
		}

		for (MHandler handler : handlerContainer.getHandlers()) {
			if (id.equals(handler.getElementId())) {
				return handler;
			}
		}

		return null;
	}

