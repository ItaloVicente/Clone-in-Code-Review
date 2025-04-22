
	default boolean schemeIsHandledByOther() {
		boolean schemeIsNotHandled = !isHandled();
		String handlerInstanceLocation = getHandlerInstanceLocation();
		boolean handlerLocationIsSet = handlerInstanceLocation != null && !handlerInstanceLocation.isEmpty();
		return schemeIsNotHandled && handlerLocationIsSet;
	}
