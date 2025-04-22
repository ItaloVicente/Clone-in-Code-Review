		} catch (CoreException exception) {
			handleCoreException(exception);
		}
		return null;
	}

	String decorateText(String text, Object element) {
		try {
			ILabelDecorator currentDecorator = internalGetDecorator();
			if (currentDecorator != null) {
