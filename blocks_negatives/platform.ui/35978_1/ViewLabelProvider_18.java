	protected void initialize(ColumnViewer viewer, ViewerColumn column) {
		super.initialize(viewer, column);
	}

	Image cacheImage(ImageDescriptor desc) {
        if (images == null) {
			images = new HashMap<ImageDescriptor, Image>(21);
		}
        Image image = images.get(desc);
        if (image == null) {
            image = desc.createImage();
            images.put(desc, image);
        }
        return image;
    }

    @Override
