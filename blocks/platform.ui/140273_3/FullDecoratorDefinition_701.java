		if (!this.enabled && decorator != null) {
			IBaseLabelProvider cached = decorator;
			decorator = null;
			disposeCachedDecorator(cached);
		}
	}

	Image decorateImage(Image image, Object element) {
		try {
			ILabelDecorator currentDecorator = internalGetDecorator();
			if (currentDecorator != null) {
