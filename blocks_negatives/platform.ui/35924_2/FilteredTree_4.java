			final Image inactiveImage = JFaceResources.getImageRegistry()
					.getDescriptor(DISABLED_CLEAR_ICON).createImage();
			final Image activeImage = JFaceResources.getImageRegistry()
					.getDescriptor(CLEAR_ICON).createImage();
			final Image pressedImage = new Image(getDisplay(), activeImage,
					SWT.IMAGE_GRAY);
