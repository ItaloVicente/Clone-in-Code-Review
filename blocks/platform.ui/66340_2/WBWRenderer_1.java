	@Override
	public Image getImage(MUILabel element) {
		Image image = super.getImage(element);
		if (image == null && element instanceof MWindow) {
			MWindow parent = modelService.getTopLevelWindowFor((MWindow) element);
			if (parent != null && parent != element) {
				image = getImage(parent);
			}
		}
		return image;
	}

