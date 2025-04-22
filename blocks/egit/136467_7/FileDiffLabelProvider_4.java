		ImageDescriptor desc = c.getBaseImageDescriptor();
		if (desc == null) {
			return null;
		}
		Image image = UIIcons.getImage(resourceManager, desc);
		desc = c.getImageDcoration();
		if (desc != null) {
			image = UIIcons.getImage(resourceManager, new DecorationOverlayIcon(
					image, desc, IDecoration.BOTTOM_RIGHT));
		}
		return image;
