
	public void testBug395126() {
		ImageDescriptor imageDescriptor = AbstractUIPlugin.imageDescriptorFromPlugin("org.eclipse.jface",
				"platform:/plugin/org.eclipse.jface/$nl$/icons/full/message_error.png");
		Image image = null;
		try {
			image = imageDescriptor.createImage(false);
			assertNotNull(image);
		} finally {
			if (image != null) {
				image.dispose();
			}
		}
	}

	public void testBug395126_missing() {
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
	}

	public void testBug474072() throws Exception {
		URL url = FileLocator.find(new URL("platform:/plugin/org.eclipse.jface/$nl$/icons/full/message_error.png"));
		ImageDescriptor imageDescriptor = AbstractUIPlugin.imageDescriptorFromPlugin("org.eclipse.jface",
				url.toString());
		Image image = null;
		try {
			image = imageDescriptor.createImage(false);
			assertNotNull(image);
		} finally {
			if (image != null) {
				image.dispose();
			}
		}
	}

	public void testBug474072_missing() throws Exception {
		String url = FileLocator.find(new URL("platform:/plugin/org.eclipse.jface/$nl$/icons/full/message_error.png"))
				.toString();
		url += "does-not-exist";
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
	}
