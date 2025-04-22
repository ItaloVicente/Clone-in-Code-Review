	private Image addSymlinkDecorationToImage(Image base) {
		DecorationOverlayIcon decorated = new DecorationOverlayIcon(base,
				UIIcons.OVR_SYMLINK, IDecoration.TOP_RIGHT);
		return (Image) this.resourceManager.get(decorated);
	}

