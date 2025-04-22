		} catch (CoreException exception) {
			handleCoreException(exception);
		}
	}

	boolean isLabelProperty(Object element, String property) {
		try { // Internal decorator might be null so be prepared
			IBaseLabelProvider currentDecorator = internalGetLabelProvider();
			if (currentDecorator != null) {
