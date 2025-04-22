		if (titleImage != null) {
			JFaceResources.getResources().destroyImage(imageDescriptor);
			titleImage = null;
		}

		clearListeners();
	}

	protected void firePropertyChange(final int propertyId) {
