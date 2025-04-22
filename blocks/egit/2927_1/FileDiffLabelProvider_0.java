	private Image getEditorImage(FileDiff diff) {
		Image image = DEFAULT;
		String name = new Path(diff.getPath()).lastSegment();
		if (name != null) {
			ImageDescriptor descriptor = PlatformUI.getWorkbench()
					.getEditorRegistry().getImageDescriptor(name);
			image = (Image) this.resourceManager.get(descriptor);
		}
		return image;
	}

	private Image getDecoratedImage(Image base, ImageDescriptor decorator) {
		DecorationOverlayIcon decorated = new DecorationOverlayIcon(base,
				decorator, IDecoration.BOTTOM_RIGHT);
		return (Image) this.resourceManager.get(decorated);
	}

