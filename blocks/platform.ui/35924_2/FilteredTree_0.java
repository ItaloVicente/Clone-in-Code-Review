			ResourceManager resourceManager = new LocalResourceManager(JFaceResources.getResources(), clearButton);

			final Image inactiveImage = resourceManager.createImage(JFaceResources.getImageRegistry().getDescriptor(
					DISABLED_CLEAR_ICON));
			final Image activeImage = resourceManager.createImage(JFaceResources.getImageRegistry()
					.getDescriptor(CLEAR_ICON));

