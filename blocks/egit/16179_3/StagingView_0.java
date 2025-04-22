	private LocalResourceManager resources = new LocalResourceManager(
			JFaceResources.getResources());

	private Image getImage(ImageDescriptor descriptor) {
		return (Image) this.resources.get(descriptor);
	}

