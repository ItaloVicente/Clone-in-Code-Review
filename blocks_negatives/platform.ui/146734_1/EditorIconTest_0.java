		ImageDescriptor imageDescriptor = AbstractUIPlugin.imageDescriptorFromPlugin("org.eclipse.jface",
				"platform:/plugin/org.eclipse.jface/$nl$/icons/does-not-exist.gif");
		Image image = null;
		try {
			image = imageDescriptor.createImage(false);
			assertNull(image);
		} finally {
			if (image != null) {
				image.dispose();
			}
		}
