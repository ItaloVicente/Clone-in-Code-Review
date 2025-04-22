		ImageDescriptor imageDescriptor = AbstractUIPlugin.imageDescriptorFromPlugin("org.eclipse.jface", url);
		Image image = null;
		try {
			image = imageDescriptor.createImage(false);
			assertNull(image);
		} finally {
			if (image != null) {
				image.dispose();
			}
		}
