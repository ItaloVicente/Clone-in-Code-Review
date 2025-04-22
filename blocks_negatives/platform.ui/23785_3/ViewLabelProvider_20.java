	protected void initialize(ColumnViewer viewer, ViewerColumn column) {
		super.initialize(viewer, column);
	}

	Image cacheImage(ImageDescriptor desc) {
        if (images == null) {
			images = new HashMap<ImageDescriptor, Image>(21);
