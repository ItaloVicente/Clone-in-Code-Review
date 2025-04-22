
	@Override
	public void reset() {
		super.reset();
		ToolItem item = getToolItem();
		Image defaultImage = (Image) item.getData(DEFAULT_IMAGE);
		if (item.getImage() != defaultImage) {
			item.setImage(defaultImage);
		}
	}
