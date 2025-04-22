		IConfigurationElement[] configElements = extension
				.getConfigurationElements();
		for (int j = 0; j < configElements.length; j++) {
			if (configElements[j].getName().equals(TAG_STATUSHANDLER)) {
				StatusHandlerDescriptor descriptor = new StatusHandlerDescriptor(
						configElements[j]);
				tracker.registerObject(extension, descriptor,
						IExtensionTracker.REF_STRONG);
