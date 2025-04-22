	@Override
	public Image getImage(Object element) {
		if (element instanceof IPerspectiveDescriptor) {
			IPerspectiveDescriptor desc = (IPerspectiveDescriptor) element;
			ImageDescriptor imageDescriptor = desc.getImageDescriptor();
			if (imageDescriptor == null) {
				imageDescriptor = WorkbenchImages.getImageDescriptor(ISharedImages.IMG_ETOOL_DEF_PERSPECTIVE);
			}
			Image image = (Image) imageCache.get(imageDescriptor);
			if (image == null) {
				image = imageDescriptor.createImage();
				imageCache.put(imageDescriptor, image);
			}
			return image;
		}
		return null;
	}
