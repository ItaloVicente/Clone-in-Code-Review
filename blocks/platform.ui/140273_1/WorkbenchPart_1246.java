		if (imageDescriptor != null) {
			JFaceResources.getResources().destroyImage(imageDescriptor);
		}

		clearListeners();
		partChangeListeners.clear();
	}

	protected void firePropertyChange(final int propertyId) {
