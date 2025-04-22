	private Image getDecoratedImage(Image base, ImageDescriptor decorator) {
		DecorationOverlayIcon decorated = new DecorationOverlayIcon(base,
				decorator, IDecoration.BOTTOM_RIGHT);
		return (Image) this.resourceManager.get(decorated);
	}

