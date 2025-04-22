	private boolean schemeIsHandledByOther(ISchemeInformation info) {
		boolean schemeIsNotHandled = !info.isHandled();
		boolean handlerLocationIsSet = info.getHandlerInstanceLocation() != null
				&& !info.getHandlerInstanceLocation().isEmpty();
		return schemeIsNotHandled && handlerLocationIsSet;
	}

