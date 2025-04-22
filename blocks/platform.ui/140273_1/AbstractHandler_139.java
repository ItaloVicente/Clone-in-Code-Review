	public boolean isHandled() {
		final Object handled = getAttributeValuesByName().get(IHandlerAttributes.ATTRIBUTE_HANDLED);
		if (handled instanceof Boolean) {
			return ((Boolean) handled).booleanValue();
		}

		return false;
	}

