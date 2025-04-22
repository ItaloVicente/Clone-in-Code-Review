	private Image addSymlinkDecorationToImage(Image base) {
		DecorationOverlayIcon decorated = new DecorationOverlayIcon(base,
				SYMLINK, IDecoration.TOP_RIGHT);
		return (Image) this.resourceManager.get(decorated);
	}

