	}

	void addListener(ILabelProviderListener listener) {
		try {
			IBaseLabelProvider currentDecorator = internalGetLabelProvider();
			if (currentDecorator != null) {
