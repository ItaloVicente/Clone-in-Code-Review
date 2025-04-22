	public static ImageDescriptor getEditorImage(final String path) {
		if (path != null && path.length() > 0) {
			final String name = new Path(path).lastSegment();
			if (name != null)
				return PlatformUI.getWorkbench().getEditorRegistry()
						.getImageDescriptor(name);
		}
		return PlatformUI.getWorkbench().getSharedImages()
				.getImageDescriptor(ISharedImages.IMG_OBJ_FILE);
	}

